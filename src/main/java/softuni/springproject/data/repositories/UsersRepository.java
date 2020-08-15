package softuni.springproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springproject.data.models.Role;
import softuni.springproject.data.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    List<User> findUsersByAuthoritiesContains(Role role);
    User findById(long id);
}
