package softuni.springproject.web.filters;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;
import softuni.springproject.services.services.AuthenticatedUserService;
import softuni.springproject.services.services.ChefsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticatedUserService authenticatedUserService;
    private final ChefsService chefsService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public UserAuthenticationSuccessHandler(
            AuthenticatedUserService authenticatedUserService,
            ChefsService chefsService) {
        super();
        this.authenticatedUserService = authenticatedUserService;
        this.chefsService = chefsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        String username = authenticatedUserService.getUsername();
        try {
            ChefDetailsServiceModel chef = chefsService.getByUsername(username);
            httpServletRequest.getSession()
                    .setAttribute("chefName", chef.getName());
        } catch (ChefNotFoundException ex) {
            // do nothing
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/home");
    }
}
