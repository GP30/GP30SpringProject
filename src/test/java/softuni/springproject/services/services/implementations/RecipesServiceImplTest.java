package softuni.springproject.services.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.services.models.recipes.RecipeServiceModel;
import softuni.springproject.services.services.validation.RecipesValidationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipesServiceImplTest {
    List<Recipe> recipes;

    RecipesRepository recipesRepository;
    ChefsRepository chefsRepository;
    RecipesValidationService recipesValidationService;

    RecipesServiceImpl service;

    @BeforeEach
    void setupTest() {
        recipes = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();

        recipesRepository = Mockito.mock(RecipesRepository.class);
        chefsRepository = Mockito.mock(ChefsRepository.class);

        Mockito.when(recipesRepository.findAll())
                .thenReturn(recipes);

        service = new RecipesServiceImpl(recipesRepository, chefsRepository, recipesValidationService, mapper);
    }

    @Test
    void getRecipesForUser_whenNoRecipes_shouldReturnEmptyList() {
        recipes.clear();

        List<RecipeServiceModel> actualRecipes = service.getRecipesForUser("username");

        assertEquals(0, actualRecipes.size());
    }

}