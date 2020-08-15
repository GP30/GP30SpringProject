package softuni.springproject.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    public String getAllUsers(Model model) {
        List<User> adminUsers = usersService.getAllAdminUsers();
        List<User> userUsers = usersService.getAllUserUsers();
        model.addAttribute("admin_users", adminUsers);
        model.addAttribute("user_users", userUsers);
        return "users/all-users.html";
    }

    @RequestMapping("/demote/{id}")
    public String demoteAdminToUser(@PathVariable("id") long id) {
        usersService.demoteAdminToUserById(id);
        return "redirect:/users/all-users";
    }

    @RequestMapping("/promote/{id}")
    public String promoteUserToAdmin(@PathVariable("id") long id) {
        usersService.promoteUserToAdminById(id);
        return "redirect:/users/all-users";
    }

}
