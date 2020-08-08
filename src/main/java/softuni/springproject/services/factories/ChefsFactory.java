package softuni.springproject.services.factories;

import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;

public interface ChefsFactory {
    Chef create(String name, Gender gender);
}
