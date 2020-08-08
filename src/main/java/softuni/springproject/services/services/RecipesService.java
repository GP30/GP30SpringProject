package softuni.springproject.services.services;

import softuni.springproject.services.models.RecipeServiceModel;

import java.util.List;

public interface RecipesService {
    List<RecipeServiceModel> getRecipesForUser(String username);
    void createForUserById(long id, String username);
}
