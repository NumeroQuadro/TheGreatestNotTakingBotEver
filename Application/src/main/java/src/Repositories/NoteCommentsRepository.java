package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.NoteComments;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteCommentsRepository extends JpaRepository<NoteComments, UUID> {
    List<NoteComments> findAllByContentDetailsId(UUID contentDetailsId);
    List<NoteComments> findByCommentId(UUID commentId);
}
