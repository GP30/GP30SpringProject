package softuni.springproject.web.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseModel {
    private long id;
    private String name;
    private int difficulty;
    private int taste;
    private boolean isKnown;
}
