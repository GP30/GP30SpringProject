package softuni.springproject.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.services.models.LoginUserServiceModel;
import softuni.springproject.services.models.auth.RegisterUserServiceModel;
import softuni.springproject.services.services.AuthService;
import softuni.springproject.web.view.models.RegisterUserModel;
import javax.validation.Valid;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper mapper;
    private final ChefsRepository chefsRepository;

    public AuthController(
            AuthService authService,
            ModelMapper mapper,
            ChefsRepository chefsRepository) {
        this.authService = authService;
        this.mapper = mapper;
        this.chefsRepository = chefsRepository;
    }

    @GetMapping("/login")
    public String getLogInForm() {
        return "auth/login.html";

    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("model", new RegisterUserModel());
        return "auth/register.html";
    }


    @PostMapping("register")
    public String register(@Valid @ModelAttribute RegisterUserModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register.html";
        }
        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);
        authService.register(serviceModel);
        return "redirect:/";
    }

    @PostMapping("login")
    public String login(@ModelAttribute RegisterUserModel model, HttpSession session) {
        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);
        try{
            LoginUserServiceModel loginUserServiceModel = authService.login(serviceModel);
            Optional<Chef> chef = chefsRepository
                    .getByUserUsername(loginUserServiceModel.getUsername());
            chef.ifPresent(value -> loginUserServiceModel.setChefName(value.getName()));
            session.setAttribute("user", loginUserServiceModel);
            return "redirect:/home";
        } catch (Exception ex){
            return "redirect:/users/login";
        }

    }

}
