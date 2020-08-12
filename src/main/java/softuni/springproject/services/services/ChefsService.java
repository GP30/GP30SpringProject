package softuni.springproject.services.services;

import softuni.springproject.data.models.Chef;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;

import java.util.List;

public interface ChefsService {
    ChefDetailsServiceModel getByName(String name);
    Chef create(ChefCreateServiceModel serviceModel);
    List<ChefDetailsServiceModel> getAllChefs();
    ChefDetailsServiceModel getByUsername(String username);
    void repUpChefs();
}
