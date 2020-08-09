package softuni.springproject.services.services;

import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.models.recipes.RecipeServiceModel;

import java.util.List;

public interface RecipesService {
    List<RecipeServiceModel> getRecipesForUser(String username);
    void addToUserById(long id, String username);
    void create(RecipeCreateServiceModel serviceModel);
    //   void createForUserById(long id, String username);
}
