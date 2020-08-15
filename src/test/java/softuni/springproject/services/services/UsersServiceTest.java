package softuni.springproject.services.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.springproject.base.TestBase;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;
import softuni.springproject.data.models.User;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.data.repositories.UsersRepository;
import softuni.springproject.services.factories.ChefsFactory;
import softuni.springproject.services.factories.base.ChefsFactoryImpl;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.services.ChefsService;
import softuni.springproject.services.services.implementations.ChefsServiceImpl;
import softuni.springproject.services.services.implementations.UsersServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest extends TestBase {
    @MockBean
    UsersRepository usersRepository;

    @Autowired
    UsersService service;

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
        Chef chef = new Chef();
        String chefName = "ChefGeorgi";
        chef.setName(chefName);
        user.setChef(chef);

        Mockito.when(usersRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        ChefCreateServiceModel chefToCreate = new ChefCreateServiceModel(chefName, Gender.MALE);

        assertThrows(Exception.class, () ->
                service.createChefForUser(user.getUsername(), chefToCreate));
    }

}