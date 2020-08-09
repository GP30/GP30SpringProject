package softuni.springproject.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.models.auth.LoginUserServiceModel;
import softuni.springproject.services.models.auth.RegisterUserServiceModel;
import softuni.springproject.services.services.AuthService;
import softuni.springproject.services.services.HashingService;
import softuni.springproject.services.services.validation.AuthValidationService;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthValidationService authValidationService;
    private final UsersRepository usersRepository;
    private final ModelMapper mapper;
    private final HashingService hashingService;

    public AuthServiceImpl(
            AuthValidationService authValidationService,
            UsersRepository usersRepository,
            ModelMapper mapper,
            HashingService hashingService) {
        this.authValidationService = authValidationService;
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.hashingService = hashingService;
    }

    @Override
    public void register(RegisterUserServiceModel model) {
        if (!authValidationService.isValid(model)) {
            // do something
            return;
        }

        User user = mapper.map(model, User.class);
        user.setPassword(hashingService.hash(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public LoginUserServiceModel login(RegisterUserServiceModel serviceModel) throws Exception {
        String passwordHash = hashingService.hash(serviceModel.getPassword());
        return usersRepository
                .findByUsernameAndPassword(serviceModel.getUsername(), passwordHash)
                .map(user -> {
                    String chefName = user.getChef() == null
                            ? null
                            : user.getChef().getName();

                    return new LoginUserServiceModel(serviceModel.getUsername(), chefName);
                })
                .orElseThrow(() -> new Exception("Invalid user"));
    }
}
