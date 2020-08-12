package softuni.springproject.services.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;

public interface UsersService extends UserDetailsService {
    void createChefForUser(String username, ChefCreateServiceModel chef) throws Exception;
}
