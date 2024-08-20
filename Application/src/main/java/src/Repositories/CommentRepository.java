package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
