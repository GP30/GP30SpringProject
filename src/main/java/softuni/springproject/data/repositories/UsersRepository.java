package softuni.springproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springproject.data.models.User;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
