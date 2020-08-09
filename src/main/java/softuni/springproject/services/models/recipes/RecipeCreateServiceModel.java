package softuni.springproject.services.models.recipes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateServiceModel {
    private String name;
    private int difficulty;
    private int taste;
}
