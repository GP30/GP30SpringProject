package softuni.springproject.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.services.models.auth.LoginUserServiceModel;
import softuni.springproject.services.models.auth.RegisterUserServiceModel;
import softuni.springproject.services.services.AuthService;
import softuni.springproject.web.view.models.RegisterUserModel;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {

    private final AuthService authService;
    private final ModelMapper mapper;

    @ModelAttribute("registerUserModel")
    public RegisterUserModel registerUserModel() {
        return new RegisterUserModel();
    }

    public AuthController(
            AuthService authService,
            ModelMapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @GetMapping("/login")
    public String getLoginForm(@RequestParam(required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("error", error);
        }
        return "auth/login.html";
    }

    @GetMapping("/register")
    public String getRegisterForm(@ModelAttribute("registerUserModel") RegisterUserModel registerUserModel) {
        return "auth/register.html";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUserModel") RegisterUserModel registerUserModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register.html";
        }
        RegisterUserServiceModel serviceModel = mapper.map(registerUserModel, RegisterUserServiceModel.class);
        authService.register(serviceModel);
        return "redirect:/";
    }

//    @PostMapping("login")
//    public String login(@ModelAttribute RegisterUserModel model, HttpSession session) {
//        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);
//        try{
//            LoginUserServiceModel loginUserServiceModel = authService.login(serviceModel);
//            Optional<Chef> chef = chefsRepository
//                    .getByUserUsername(loginUserServiceModel.getUsername());
//            chef.ifPresent(value -> loginUserServiceModel.setChefName(value.getName()));
//            session.setAttribute("user", loginUserServiceModel);
//            return "redirect:/home";
//        } catch (Exception ex){
//            return "redirect:/users/login";
//        }
//    }

}
