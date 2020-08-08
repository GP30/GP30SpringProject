package softuni.springproject.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springproject.data.models.Recipe;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {

}