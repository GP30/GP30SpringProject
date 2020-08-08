package softuni.springproject.services.services;

import softuni.springproject.services.models.LoginUserServiceModel;
import softuni.springproject.services.models.auth.RegisterUserServiceModel;

public interface AuthService {
    void register(RegisterUserServiceModel model);
    LoginUserServiceModel login(RegisterUserServiceModel serviceModel) throws Exception;
}