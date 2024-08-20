package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.Description;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Integer> {
}
