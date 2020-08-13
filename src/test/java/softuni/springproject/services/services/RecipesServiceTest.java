package softuni.springproject.services.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.springproject.base.TestBase;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.models.recipes.RecipeServiceModel;
import softuni.springproject.services.services.implementations.RecipesServiceImpl;
import softuni.springproject.services.services.validation.RecipesValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RecipesServiceTest extends TestBase {
    List<Recipe> recipes;

    @MockBean
    RecipesRepository recipesRepository;
    @MockBean
    ChefsRepository chefsRepository;
    @MockBean
    RecipesValidationService recipesValidationService;

    @Autowired
    RecipesService service;

    @Override
    protected void beforeEach() {
        recipes = new ArrayList<>();

        Mockito.when(recipesRepository.findAll())
                .thenReturn(recipes);
    }

    @Test
    void getRecipesForUser_whenNoRecipes_shouldReturnEmptyList() {
        recipes.clear();

        List<RecipeServiceModel> actualRecipes = service.getRecipesForUser("username");

        assertEquals(0, actualRecipes.size());
    }

    @Test
    void getRecipesForUser_whenRecipesButNotForUser_shouldReturnRecpesWithoutKnown() {
        recipes.clear();
        recipes.addAll(getRecipes());

        List<RecipeServiceModel> actualRecipes = service.getRecipesForUser("username");

        assertEquals(recipes.size(), actualRecipes.size());
        boolean hasNoOwned = actualRecipes.stream()
                .filter(RecipeServiceModel::isKnown)
                .findAny()
                .isEmpty();

        assertTrue(hasNoOwned);
    }

    @Test
    void getRecipesForUser_whenRecipesAndOneForUser_shouldReturnRecipesWithOneForUser() {
        recipes.clear();
        recipes.addAll(getRecipes());

        Chef chef = new Chef();
        User user = new User();
        user.setUsername("username");
        chef.setUser(user);

        recipes.get(0)
                .setChefs(new ArrayList<>());
        recipes.get(0)
                .getChefs()
                .add(chef);

        List<RecipeServiceModel> actualRecipes = service.getRecipesForUser(user.getUsername());

        assertEquals(recipes.size(), actualRecipes.size());

        List<RecipeServiceModel> recipesForUser = actualRecipes.stream()
                .filter(RecipeServiceModel::isKnown)
                .collect(Collectors.toList());

        assertEquals(1, recipesForUser.size());
        assertEquals(recipes.get(0).getId(), recipesForUser.get(0).getId());
    }

    @Test
    void addToUserById_whenRecipeDoesNotExist_shouldThrowException() {
        recipes.clear();
        String username = "username";
        Mockito.when(recipesRepository.findById(1L))
                .thenReturn(Optional.of(new Recipe()));

        assertThrows(
                NullPointerException.class,
                () -> service.addToUserById(1, username));

    }

    @Test
    void addToUserById_whenUserHasNoChef_shouldThrowException() {
        recipes.clear();
        recipes.addAll(getRecipes());

        String username = "username";
        Mockito.when(chefsRepository.getByUserUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(
                NullPointerException.class,
                () -> service.addToUserById(recipes.get(0).getId(), username));
    }

    @Test
    void addToUserById_whenUserHasChefAndRecipeExists_shouldBeSaved() {
        recipes.clear();
        recipes.addAll(getRecipes());

        String username = "username";
        Chef chef= new Chef();
        chef.setRecipes(new ArrayList<>());
        Mockito.when(chefsRepository.getByUserUsername(username))
                .thenReturn(Optional.of(chef));

        Mockito.when(recipesRepository.findById(1L))
                .thenReturn(Optional.of(recipes.get(0)));

        service.addToUserById(recipes.get(0).getId(), username);

        ArgumentCaptor<Chef> argument = ArgumentCaptor.forClass(Chef.class);
        Mockito.verify(chefsRepository).saveAndFlush(argument.capture());

        Chef theChef = argument.getValue();
        assertEquals(1, theChef.getRecipes().size());
        assertEquals(recipes.get(0).getId(), theChef.getRecipes().get(0).getId());
    }

    @Test
    void create_whenRecipeIsValid_shouldThrow() {
        RecipeCreateServiceModel recipe = getRecipeCreateModel();

        Mockito.when(recipesValidationService.isValid(recipe))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> service.create(recipe));
    }

    @Test
    void create_whenAllIsValid_shouldThrow() {
        RecipeCreateServiceModel recipeModel = getRecipeCreateModel();

        Mockito.when(recipesValidationService.isValid(recipeModel))
                .thenReturn(true);

        service.create(recipeModel);


        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        Mockito.verify(recipesRepository).save(argument.capture());

        Recipe recipe = argument.getValue();
        assertNotNull(recipe);
    }



    private RecipeCreateServiceModel getRecipeCreateModel() {
        return new RecipeCreateServiceModel("Recipe 1", 1, 2);
    }

    private List<Recipe> getRecipes() {
        return getRecipes(2);
    }

    private List<Recipe> getRecipes(int count) {
        return IntStream.range(0, count)
                .map(x -> x + 1)
                .mapToObj(id -> {
                    Recipe recipe = new Recipe();
                    recipe.setId(id);
                    recipe.setName("Recipe " + id);
                    recipe.setDifficulty(1 * id);
                    recipe.setTaste(2 * id);
                    return recipe;
                })
                .collect(Collectors.toList());
    }

}