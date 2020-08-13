package softuni.springproject.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChefHomeModel {
    private String name;
    private String gender;
    private int skill;
    private int reputation;
}
