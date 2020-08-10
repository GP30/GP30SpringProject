package softuni.springproject.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
    private final ChefsService chefsService;
    private final UsersRepository usersRepository;
    private ChefsRepository chefsRepository;
    private final ModelMapper mapper;

    public UsersServiceImpl(ChefsService chefsService,
                            UsersRepository usersRepository,
                            ModelMapper mapper,
                            ChefsRepository chefsRepository) {
        this.chefsService = chefsService;
        this.usersRepository = usersRepository;
        this.chefsRepository = chefsRepository;
        this.mapper = mapper;
    }

    @Override
    public void createChefForUser(String username, ChefCreateServiceModel chefServiceModel) throws Exception {
        User user = usersRepository.findByUsername(username);
        if(user.getChef() != null) {
            throw new Exception("User already has a chef.");
        }
        Chef chef = chefsService.create(chefServiceModel);
        chef.setUser(user);
        chefsRepository.save(chef);
    }
}
