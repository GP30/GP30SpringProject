package softuni.springproject.services.services;

import softuni.springproject.services.models.chefs.ChefCreateServiceModel;

public interface UsersService {
    void createChefForUser(String username, ChefCreateServiceModel chef) throws Exception;
}
