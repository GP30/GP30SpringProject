package softuni.springproject.services.factories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;
import softuni.springproject.services.base.ServiceTestBase;

import static org.junit.jupiter.api.Assertions.*;
import static softuni.springproject.services.factories.ChefsConstants.*;

class ChefsFactoryTest extends ServiceTestBase {
    @Autowired
    ChefsFactory factory;

    @Test
    void create_withNameAndGender_shouldReturnChefWithDefaultProps() {
        String name = "Chef";
        Gender gender = Gender.MALE;

        Chef chef = factory.create(name, gender);

        assertEquals(name, chef.getName());
        assertEquals(gender, chef.getGender());
        assertEquals(INITIAL_SKILL, chef.getSkill());
        assertEquals(INITIAL_REPUTATION, chef.getReputation());
    }
}