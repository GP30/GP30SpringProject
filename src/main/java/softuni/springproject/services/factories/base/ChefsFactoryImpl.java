package softuni.springproject.services.factories.base;

import softuni.springproject.config.annotations.Factory;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;
import softuni.springproject.services.factories.ChefsFactory;

@Factory
public class ChefsFactoryImpl implements ChefsFactory {
    @Override
    public Chef create(String name, Gender gender) {
        Chef chef = new Chef();
        chef.setName(name);
        chef.setGender(gender);
        chef.setSkill(1);
        chef.setReputation(1);
        return chef;

    }
}
