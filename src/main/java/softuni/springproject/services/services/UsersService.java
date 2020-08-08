package softuni.springproject.services.services;

import softuni.springproject.services.models.ChefCreateServiceModel;
import softuni.springproject.services.models.ChefDetailsServiceModel;

public interface UsersService {
    void createChefForUser(String username, ChefCreateServiceModel chef) throws Exception;
}
