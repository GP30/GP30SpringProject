package softuni.springproject.services.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.factories.ChefsFactory;
import softuni.springproject.services.factories.base.ChefsFactoryImpl;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ChefsServiceImplTest {
    ChefsRepository chefsRepository;
    ChefsFactory chefsFactory;
    ModelMapper mapper;

    ChefsServiceImpl service;

    @BeforeEach
    void setupTest() {
        chefsRepository = Mockito.mock(ChefsRepository.class);
        chefsFactory = new ChefsFactoryImpl();
        mapper = new ModelMapper();

        service = new ChefsServiceImpl(chefsRepository, chefsFactory, mapper);
    }

    @Test
    void getByName_whenChefDoesNotExist_shouldThrowChefNotFoundException() {
        String chefName = "Chef Name";

        Mockito.when(chefsRepository.getByNameIgnoreCase(chefName))
                .thenReturn(Optional.empty());

        assertThrows(
                ChefNotFoundException.class,
                () -> service.getByName(chefName));
    }

    @Test
    void getByName_whenChefDoesExist_shouldReturnChef() {
        String chefName = "Chef name";

        Chef chef = new Chef();
        chef.setName(chefName);
        chef.setRecipes(new ArrayList<>());

        Mockito.when(chefsRepository.getByNameIgnoreCase(chefName))
                .thenReturn(Optional.of(chef));

        ChefDetailsServiceModel chefDetails = service.getByName(chefName);

        assertEquals(chef.getName(), chefDetails.getName());
    }

}