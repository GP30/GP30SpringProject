package softuni.springproject.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserServiceModel {
    private String username;
    private String chefName;

    public LoginUserServiceModel(String username, String chefName) {
        this.username = username;
        this.chefName = chefName;
    }
}
