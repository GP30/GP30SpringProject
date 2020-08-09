package softuni.springproject.services.models.recipes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeServiceModel {
    private long id;
    private String name;
    private int difficulty;
    private int taste;
    private boolean isKnown;
}
