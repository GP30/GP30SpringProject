package softuni.springproject.services.services.implementations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.models.recipes.RecipeServiceModel;
import softuni.springproject.services.services.RecipesService;
import softuni.springproject.services.services.validation.RecipesValidationService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private final RecipesRepository recipesRepository;
    private final ChefsRepository chefsRepository;
    private final RecipesValidationService recipesValidationService;
    private final ModelMapper mapper;

    @Override
    public List<RecipeServiceModel> getRecipesForUser(String username) {
        return recipesRepository.findAll()
                .stream()
                .map(recipe -> {
                    RecipeServiceModel serviceModel = mapper.map(recipe, RecipeServiceModel.class);
                    if (recipe.getChefs() != null) {
                        Chef chef = recipe.getChefs()
                                .stream()
                                .filter(h -> h.getUser().getUsername().equals(username))
                                .findAny()
                                .orElse(null);

                        serviceModel.setKnown(chef != null);
                    }
                    return serviceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addToUserById(long id, String username) {
        Optional<Chef> chefResult = chefsRepository.getByUserUsername(username);
        if (chefResult.isEmpty()) {
            throw new NullPointerException("User does not have a chef.");
        }

        Optional<Recipe> recipeResult = recipesRepository.findById(id);
        if (recipeResult.isEmpty()) {
            throw new NullPointerException("Recipe does not exists");
        }

        Chef chef = chefResult.get();
        Recipe recipe = recipeResult.get();
        boolean hasRecipe = false;
        for (Recipe currRecipe: chef.getRecipes() ) {
            if (currRecipe.getName() == recipe.getName()) {
                hasRecipe = true;
                break;
            }
        }
        if (!hasRecipe) {
            chef.getRecipes().add(recipe);
            chef.setSkill(chef.getSkill() + recipe.getDifficulty());
            chef.setReputation(chef.getReputation() + recipe.getTaste());
            chefsRepository.saveAndFlush(chef);
        }
    }

    @Override
    public void create(RecipeCreateServiceModel serviceModel) {
        if (!this.recipesValidationService.isValid(serviceModel)) {
            throw new RuntimeException("Chef is invalid");
        }

        Recipe recipe = mapper.map(serviceModel, Recipe.class);
        recipesRepository.save(recipe);
    }

    @Override
    public List<RecipeServiceModel> getAll() {
        return recipesRepository.findAll()
                .stream()
                .map(recipe -> mapper.map(recipe, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}
