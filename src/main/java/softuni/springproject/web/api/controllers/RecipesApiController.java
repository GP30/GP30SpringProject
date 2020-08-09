package softuni.springproject.web.api.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.services.RecipesService;
import softuni.springproject.web.api.models.RecipeCreateRequestModel;
import softuni.springproject.web.api.models.RecipeResponseModel;
import softuni.springproject.web.base.BaseController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @PostMapping("/api/recipes/add-to-user/{id}")
    public void addRecipe(@PathVariable long id, HttpSession session, HttpServletResponse response) throws IOException {
        String username = getUsername(session);
        recipesService.addToUserById(id, username);
        String chefName = getChefName(session);
        response.sendRedirect("/chefs/details/" + chefName);
    }

    @PostMapping("/api/recipes")
    public void create(RecipeCreateRequestModel requestModel, HttpServletResponse response) throws IOException {
        RecipeCreateServiceModel serviceModel = mapper.map(requestModel, RecipeCreateServiceModel.class);
        recipesService.create(serviceModel);
        response.sendRedirect("/recipes/recipebook");
    }

}
