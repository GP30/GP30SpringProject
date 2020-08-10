package softuni.springproject.services.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.springproject.base.TestBase;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.services.models.recipes.RecipeServiceModel;
import softuni.springproject.services.services.implementations.RecipesServiceImpl;
import softuni.springproject.services.services.validation.RecipesValidationService;

import java.util.ArrayList;
import java.util.List;

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

}