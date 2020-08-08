package softuni.springproject.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.springproject.data.models.Gender;

@Getter
@Setter
@NoArgsConstructor
public class ChefDetailsServiceModel {
    private String name;
    private Gender gender;
    private int skill;
    private int reputation;
}
