package softuni.springproject.web.controllers.base;

import softuni.springproject.services.models.LoginUserServiceModel;

import javax.servlet.http.HttpSession;

public class BaseController {
    protected boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    protected String getUsername(HttpSession session) {
        return ((LoginUserServiceModel) session.getAttribute("user")).getUsername();
    }
}
