package softuni.springproject.services.services.validation;

import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;

public interface RecipesValidationService {
    boolean isValid(RecipeCreateServiceModel serviceModel);
}
