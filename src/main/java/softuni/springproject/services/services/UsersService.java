package softuni.springproject.services.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.springproject.data.models.Role;
import softuni.springproject.data.models.User;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;

import java.util.List;

public interface UsersService extends UserDetailsService {
    void createChefForUser(String username, ChefCreateServiceModel chef) throws Exception;
    List<User> getAllUsers();

    List<User> getAllUsersByRole(Role role);

    List<User> getAllUserUsers();

    List<User> getAllAdminUsers();

    void demoteAdminToUser(User user);

    void promoteUserToAdmin(User user);
}
