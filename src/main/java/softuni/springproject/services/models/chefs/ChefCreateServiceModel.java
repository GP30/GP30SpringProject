package softuni.springproject.services.models.chefs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.springproject.data.models.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChefCreateServiceModel {
    private String name;
    private Gender gender;
}
