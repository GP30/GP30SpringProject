package softuni.springproject.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import softuni.springproject.data.models.User;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.UsersService;
import softuni.springproject.web.view.models.ChefDetailsViewModel;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final ChefsService chefsService;
    private final ModelMapper mapper;
    private final UsersService usersService;

    @GetMapping("/profile")
    public ModelAndView getProfile(ModelAndView modelAndView, Principal principal) {
        modelAndView.setViewName("users/profile");
        ChefDetailsServiceModel serviceModel = chefsService.getByUsername(principal.getName());
        ChefDetailsViewModel viewModel = mapper.map(serviceModel, ChefDetailsViewModel.class);
        modelAndView.addObject("chef", viewModel);
        return modelAndView;
    }

    @GetMapping("/all-users")
    public String getAllUsers(Model model) {
        List<User> adminUsers = usersService.getAllAdminUsers();
        List<User> userUsers = usersService.getAllUserUsers();
        model.addAttribute("admin_users", adminUsers);
        model.addAttribute("user_users", userUsers);
        return "users/all-users.html";
    }

    @PutMapping(value = "/demote")
    public String demoteAdminToUser(@RequestParam User user) {
        usersService.demoteAdminToUser(user);
        return "users/all-users.html";
    }

    @PutMapping("/promote")
    public String promoteUserToAdmin(@RequestParam User user) {
        usersService.promoteUserToAdmin(user);
        return "users/all-users.html";
    }

}
