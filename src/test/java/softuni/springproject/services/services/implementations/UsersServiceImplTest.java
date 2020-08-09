package softuni.springproject.services.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.factories.ChefsFactory;
import softuni.springproject.services.factories.base.ChefsFactoryImpl;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.services.ChefsService;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {
    ChefsService chefsService;
    UsersRepository usersRepository;
    ChefsRepository chefsRepository;
    ChefsFactory chefsFactory;

    UsersServiceImpl service;

    @BeforeEach
    public void setupTest() {
        usersRepository = Mockito.mock(UsersRepository.class);
        chefsRepository = Mockito.mock(ChefsRepository.class);
        chefsFactory = new ChefsFactoryImpl();
        ModelMapper mapper = new ModelMapper();
        chefsService = new ChefsServiceImpl(chefsRepository, chefsFactory, mapper);
        service = new UsersServiceImpl(chefsService, usersRepository, mapper, chefsRepository);
    }

    @Test
    public void createChefForUser_whenUserExistsAndDoesNotHaveAChef_shouldCreateChefForUser() throws Exception {
        User user = new User();
        user.setUsername("Georgi");
        String chefName = "GotvachGeorgi";
        Mockito.when(usersRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        ChefCreateServiceModel chefToCreate = new ChefCreateServiceModel(chefName, Gender.MALE);

        service.createChefForUser(user.getUsername(), chefToCreate);

        assertEquals(chefName, user.getChef().getName());
    }

    @Test
    public void createChefForUser_whenUserExistsAndHasAChef_shouldThrowException() {
        User user = new User();
        user.setUsername("Georgi");
        user.setChef(new Chef());
        String chefName = "ChefGeorgi";
        Mockito.when(usersRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        ChefCreateServiceModel chefToCreate = new ChefCreateServiceModel(chefName, Gender.MALE);

        assertThrows(Exception.class, () ->
                service.createChefForUser(user.getUsername(), chefToCreate));
    }

}