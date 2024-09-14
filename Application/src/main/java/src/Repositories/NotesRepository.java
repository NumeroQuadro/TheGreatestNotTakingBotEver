package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.Notes;

import java.util.UUID;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {
}
