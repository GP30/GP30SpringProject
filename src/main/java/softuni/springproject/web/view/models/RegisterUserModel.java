package softuni.springproject.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserModel {
    @Size(min = 3, max = 15)
    @Pattern(regexp = "[A-Z].*", message = "Must start with a capital letter!")
    private String username;

    @NotBlank(message = "Email is Mandatory!")
    @Email
    private String email;

    @Size(min = 3, max = 15)
    private String password;
    @Size(min = 3, max = 15)
    private String confirmPassword;
}
