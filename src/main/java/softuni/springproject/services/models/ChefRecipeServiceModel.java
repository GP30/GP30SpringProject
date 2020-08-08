package softuni.springproject.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChefRecipeServiceModel {
    private String name;
    private int difficulty;
    private int taste;
}
