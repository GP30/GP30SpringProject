package softuni.springproject.web.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/recipes")
@Controller
public class RecipesController {

    @GetMapping("/recipebook")
    public String getRecipebook(){
        return "recipes/recipebook.html";
    }

    @GetMapping("/create")
    public String getCreateRecipeForm(){
        return "recipes/create-recipe.html";
    }
}
