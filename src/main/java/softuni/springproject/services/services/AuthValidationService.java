package softuni.springproject.services.services;

import softuni.springproject.services.models.auth.RegisterUserServiceModel;

public interface AuthValidationService {
    boolean isValid(RegisterUserServiceModel user);
}
