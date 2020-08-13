package softuni.springproject.web.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;
import softuni.springproject.services.services.AuthenticatedUserService;
import softuni.springproject.services.services.ChefsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserChefInterceptor implements HandlerInterceptor {
    private final AuthenticatedUserService authenticatedUserService;
    private final ChefsService chefsService;

    public UserChefInterceptor(
            AuthenticatedUserService authenticatedUserService, ChefsService chefsService) {
        this.authenticatedUserService = authenticatedUserService;
        this.chefsService = chefsService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String username = authenticatedUserService.getUsername();
        try {
            ChefDetailsServiceModel chef = chefsService.getByUsername(username);
            request.getSession()
                    .setAttribute("chefName", chef.getName());
        } catch (ChefNotFoundException ex) {
            // do nothing
        }
    }
}
