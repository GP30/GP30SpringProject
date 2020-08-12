package softuni.springproject.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.UsersService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {
    private final ChefsService chefsService;
    private final UsersRepository usersRepository;
    private final ChefsRepository chefsRepository;

    public UsersServiceImpl(ChefsService chefsService, UsersRepository usersRepository, ChefsRepository chefsRepository) {
        this.chefsService = chefsService;
        this.usersRepository = usersRepository;
        this.chefsRepository = chefsRepository;
    }

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
}
