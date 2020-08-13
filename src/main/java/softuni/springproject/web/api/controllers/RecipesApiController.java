package softuni.springproject.web.api.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.springproject.services.models.recipes.RecipeCreateServiceModel;
import softuni.springproject.services.services.RecipesService;
import softuni.springproject.web.api.models.RecipeCreateRequestModel;
import softuni.springproject.web.api.models.RecipeResponseModel;
import softuni.springproject.web.base.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RecipesApiController extends BaseController {
    private final RecipesService recipesService;
    private final ModelMapper mapper;

    @GetMapping(value = "/api/recipes")
    public ResponseEntity<List<RecipeResponseModel>> getRecipes(Principal principal) {
        String username = principal.getName();
        List<RecipeResponseModel> result = recipesService.getRecipesForUser(username)
                .stream()
                .map(recipe -> mapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/api/recipes-all")
    public List<RecipeResponseModel> getRecipes() throws InterruptedException {
        return recipesService.getAll()
                .stream()
                .map(recipe -> mapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/recipes/add-to-user/{id}")
    public void addRecipe(@PathVariable long id, Principal principal , HttpSession session, HttpServletResponse response) throws IOException {
        String username = principal.getName();
        recipesService.addToUserById(id, username);
        String chefName = getChefName(session);
        response.sendRedirect("/chefs/details/" + chefName);
    }

    @PostMapping("/api/recipes")
    public ResponseEntity<Void> create(RecipeCreateRequestModel requestModel) {
        RecipeCreateServiceModel serviceModel = mapper.map(requestModel, RecipeCreateServiceModel.class);
        recipesService.create(serviceModel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/recipes/recipebook");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
