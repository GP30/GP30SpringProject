package softuni.springproject.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No such chef")
public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException(String message) {
        super(message);
    }
}
