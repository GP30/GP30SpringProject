package softuni.springproject.web.api.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.web.api.models.RecipeResponseModel;
import softuni.springproject.web.base.ApiTestBase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RecipesApiControllerTest extends ApiTestBase {

    @MockBean
    RecipesRepository recipesRepository;

    @Test
    void getAllRecipes_whenRecipes_shouldReturnRecipes() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Pork with Potatoes");

        Mockito.when(recipesRepository.findAll())
                .thenReturn(List.of(recipe));

        RecipeResponseModel[] result = getRestTemplate()
                .getForObject(getFullUrl("/api/recipes-all"), RecipeResponseModel[].class);
        assertEquals(1, result.length);
        assertEquals(recipe.getId(), result[0].getId());
        assertEquals(recipe.getName(), result[0].getName());
    }

}