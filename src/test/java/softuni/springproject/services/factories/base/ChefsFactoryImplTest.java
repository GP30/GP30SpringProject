package softuni.springproject.services.factories.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;

import static org.junit.jupiter.api.Assertions.*;
import static softuni.springproject.services.factories.ChefsConstants.*;

class ChefsFactoryImplTest {

    ChefsFactoryImpl factory;

    @BeforeEach
    void setupTest() {
        factory = new ChefsFactoryImpl();
    }

    @Test
    void create_withNameAndGender_shouldReturnChefWithDefaultProps() {
        String name = "Gotvach";
        Gender gender = Gender.MALE;

        Chef chef = factory.create(name, gender);

        assertEquals(name, chef.getName());
        assertEquals(gender, chef.getGender());
        assertEquals(INITIAL_SKILL, chef.getSkill());
        assertEquals(INITIAL_REPUTATION, chef.getReputation());
    }

}