package softuni.springproject.services.services.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import softuni.springproject.base.TestBase;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;

import static org.junit.jupiter.api.Assertions.*;

class RecipesValidationServiceTest extends TestBase {
    @Autowired
    RecipesValidationService service;

    @Test
    void isValid_whenNameIsNull_shouldReturnFalse() {
        RecipeCreateServiceModel serviceModel = new RecipeCreateServiceModel(null, 1, 2);
        boolean isValid = service.isValid(serviceModel);
        assertFalse(isValid);
    }

    @Test
    void isValid_whenDifficultyIsNegative_shouldReturnFalse() {
        RecipeCreateServiceModel serviceModel = new RecipeCreateServiceModel("Valid name", -1, 2);
        boolean isValid = service.isValid(serviceModel);
        assertFalse(isValid);
    }

    @Test
    void isValid_whenRecipeIsValid_shouldReturnTrue() {
        RecipeCreateServiceModel serviceModel = new RecipeCreateServiceModel("Valid name", 1, 2);
        boolean isValid = service.isValid(serviceModel);
        assertTrue(isValid);
    }
}