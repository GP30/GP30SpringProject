package softuni.springproject.services.services.implementations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Role;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.RolesRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.UsersService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final ChefsService chefsService;
    private final UsersRepository usersRepository;
    private final ChefsRepository chefsRepository;
    private final RolesRepository rolesRepository;

    @Override
    public void createChefForUser(String username, ChefCreateServiceModel chefServiceModel) throws Exception {
        User user = usersRepository.findByUsername(username);
        if (user.getChef() != null) {
            throw new Exception("User already has a chef.");
        }
        Chef chef = chefsService.create(chefServiceModel);
        chef.setUser(user);
        chefsRepository.save(chef);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(s);

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByRole(Role role) {
        return usersRepository.findUsersByAuthoritiesContains(role);
    }

    @Override
    public List<User> getAllUserUsers() {
        return usersRepository.findUsersByAuthoritiesContains(rolesRepository.findDistinctByAuthority("USER"));
    }

    @Override
    public List<User> getAllAdminUsers() {
        return usersRepository.findUsersByAuthoritiesContains(rolesRepository.findDistinctByAuthority("ADMIN"));
    }

    @Override
    public void changeRoleIDtoUserId(long userId, long roleId) {
        Set<Role> roles = new HashSet<>();
        roles.add(rolesRepository.findById(roleId));
        User user = usersRepository.findById(userId);
        user.getAuthorities().clear();
        user.setAuthorities(roles);
        usersRepository.save(user);
    }

    @Override
    public void demoteAdminToUserById(long userId) {
        changeRoleIDtoUserId(userId, 1);
    }

    @Override
    public void promoteUserToAdminById(long userId) {
        changeRoleIDtoUserId(userId, 2);
    }
}
