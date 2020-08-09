package softuni.springproject.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.web.base.BaseController;
import softuni.springproject.web.view.models.ChefHomeModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController extends BaseController {
    private final ChefsService chefsService;
    private final ModelMapper modelMapper;


    @GetMapping("/")
    public String getIndex(HttpSession session) {
        return "home/index.html";
    }

    @GetMapping("/home")
    public ModelAndView getHome(ModelAndView modelAndView, HttpSession session) {
        modelAndView.setViewName("home/home");
        List<ChefHomeModel> chefs = chefsService
                .getAllChefs()
                .stream()
                .map(chef -> modelMapper.map(chef, ChefHomeModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("chefs", chefs);
        return modelAndView;
    }


}
