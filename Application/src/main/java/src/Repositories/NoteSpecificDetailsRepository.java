package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.NoteSpecificDetails;

@Repository
public interface NoteSpecificDetailsRepository extends JpaRepository<NoteSpecificDetails, Integer> {
}
