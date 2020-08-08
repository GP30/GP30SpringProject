package softuni.springproject.services.services;

import softuni.springproject.data.models.Chef;
import softuni.springproject.services.models.ChefCreateServiceModel;
import softuni.springproject.services.models.ChefDetailsServiceModel;

public interface ChefsService {
    ChefDetailsServiceModel getByName(String name);
    Chef create(ChefCreateServiceModel serviceModel);
}
