package softuni.springproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.springproject.data.models.Chef;
import java.util.Optional;

public interface ChefsRepository extends JpaRepository<Chef, Long> {
    Optional<Chef> getByNameIgnoreCase(String name);
    Optional<Chef> getByUserUsername(String username);
}