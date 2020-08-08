package softuni.springproject.services.services.implementations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Recipe;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RecipesRepository;
import softuni.springproject.services.models.RecipeServiceModel;
import softuni.springproject.services.services.RecipesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private final RecipesRepository recipesRepository;
    private final ChefsRepository chefsRepository;
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
    public void createForUserById(long id, String username) {
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
        chef.getRecipes().add(recipe);

        chefsRepository.saveAndFlush(chef);
    }
}
