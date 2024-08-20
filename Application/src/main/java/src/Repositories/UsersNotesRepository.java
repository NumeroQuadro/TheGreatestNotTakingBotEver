package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.UsersNotes;
import src.Models.UsersNotesId;

@Repository
public interface UsersNotesRepository extends JpaRepository<UsersNotes, UsersNotesId> {
}
