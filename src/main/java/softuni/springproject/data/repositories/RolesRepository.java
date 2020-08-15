package softuni.springproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springproject.data.models.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Role findDistinctByAuthority(String authority);
    Role findById(long id);
}
