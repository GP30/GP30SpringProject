package softuni.springproject.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChefRecipeViewModel {
    private String name;
    private int difficulty;
    private int taste;

}
