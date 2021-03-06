package softuni.springproject.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;
import softuni.springproject.services.models.auth.LoginUserServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.UsersService;
import softuni.springproject.web.base.BaseController;
import softuni.springproject.web.view.models.ChefCreateModel;
import softuni.springproject.web.view.models.ChefDetailsViewModel;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chefs")
@AllArgsConstructor
public class ChefsController extends BaseController {
    public final static String CHEFS_CHEF_DETAILS_VIEW_NAME = "chefs/chef-details.html";
    private final ChefsService chefsService;
    private final ModelMapper mapper;
    private final UsersService usersService;

    @GetMapping("/details/{name}")
    public ModelAndView getChefDetails(@PathVariable String name, ModelAndView modelAndView) {
        ChefDetailsServiceModel serviceModel = chefsService.getByName(name);
        ChefDetailsViewModel viewModel = mapper.map(serviceModel, ChefDetailsViewModel.class);
        modelAndView.addObject("chef", viewModel);
        modelAndView.setViewName(CHEFS_CHEF_DETAILS_VIEW_NAME);
        return modelAndView;

    }

    @GetMapping("/create")
    public String getCreateChefForm(HttpSession session) {
        if(!isAuthenticated(session)) {
            return "redirect:/users/login";
        }
        return "chefs/create-chef.html";

    }

    @PostMapping("/create")
    public String createChef(@ModelAttribute ChefCreateModel chef, HttpSession session) {
        if(!isAuthenticated(session)) {
            return "/";
        }

        String username = getUsername(session);

        ChefCreateServiceModel serviceModel = mapper.map(chef, ChefCreateServiceModel.class);
        try {
            usersService.createChefForUser(username, serviceModel);
            LoginUserServiceModel loginUserServiceModel = new LoginUserServiceModel(username, chef.getName());
            session.setAttribute("user", loginUserServiceModel);
            return "redirect:/chefs/details/" + chef.getName();
        } catch (Exception ex) {
            return "redirect:/chefs/create";
        }
    }

    @ExceptionHandler(ChefNotFoundException.class)
    public ModelAndView handleException(ChefNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }
}
