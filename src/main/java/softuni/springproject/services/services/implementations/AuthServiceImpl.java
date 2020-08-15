package softuni.springproject.services.services.implementations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Role;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.RolesRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.models.auth.LoginUserServiceModel;
import softuni.springproject.services.models.auth.RegisterUserServiceModel;
import softuni.springproject.services.services.AuthService;
import softuni.springproject.services.services.HashingService;
import softuni.springproject.services.services.validation.AuthValidationService;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthValidationService authValidationService;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final ModelMapper mapper;
    private final HashingService hashingService;

    @Override
    public void register(RegisterUserServiceModel model) {
        if (!authValidationService.isValid(model)) {
            // do something
            return;
        }

        User user = mapper.map(model, User.class);
        user.setPassword(hashingService.hash(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(rolesRepository.findById(1));
        user.setAuthorities(roles);

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
