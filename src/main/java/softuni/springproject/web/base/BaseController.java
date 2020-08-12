package softuni.springproject.web.base;

import softuni.springproject.services.models.auth.LoginUserServiceModel;

import javax.servlet.http.HttpSession;

public class BaseController {

    protected String getUsername(HttpSession session) {
        return ((LoginUserServiceModel) session.getAttribute("user")).getUsername();
    }

    protected String getChefName(HttpSession session) {
        return ((LoginUserServiceModel) session.getAttribute("user")).getChefName();
    }
}
