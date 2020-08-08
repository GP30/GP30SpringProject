package softuni.springproject.web.api.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.springproject.services.services.RecipesService;
import softuni.springproject.web.api.models.RecipeResponseModel;
import softuni.springproject.web.base.BaseController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RecipesApiController extends BaseController {
    private final RecipesService recipesService;
    private final ModelMapper mapper;

    @GetMapping(value = "/api/recipes")
    public List<RecipeResponseModel> getRecipes(HttpSession session) throws InterruptedException {
        String username = getUsername(session);
        return recipesService.getRecipesForUser(username)
                .stream()
                .map(recipe -> mapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/recipes/{id}")
    public void AddRecipe(@PathVariable long id, HttpSession session) {
        String username = getUsername(session);
        recipesService.createForUserById(id, username);
    }

}
