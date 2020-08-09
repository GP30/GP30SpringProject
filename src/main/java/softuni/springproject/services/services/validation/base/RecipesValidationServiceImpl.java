package softuni.springproject.services.services.validation.base;

import org.springframework.stereotype.Service;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.services.validation.RecipesValidationService;

@Service
public class RecipesValidationServiceImpl implements RecipesValidationService {
    public boolean isValid(RecipeCreateServiceModel serviceModel) {
        return serviceModel != null &&
                serviceModel.getName() != null &&
                serviceModel.getDifficulty() > 0 &&
                serviceModel.getTaste() > 0;
    }
}
