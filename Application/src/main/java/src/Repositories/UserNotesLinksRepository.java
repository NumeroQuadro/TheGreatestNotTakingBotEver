package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.UsersNotesLinks;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserNotesLinksRepository extends JpaRepository<UsersNotesLinks, UUID> {
    List<UsersNotesLinks> findALlByUserId(UUID userId);
}
