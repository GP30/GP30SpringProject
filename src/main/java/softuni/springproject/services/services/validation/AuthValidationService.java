package softuni.springproject.services.services.validation;

import softuni.springproject.services.models.auth.RegisterUserServiceModel;

public interface AuthValidationService {
    boolean isValid(RegisterUserServiceModel user);
}
