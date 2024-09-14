package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.ContentDetails;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContentDetailsRepository extends JpaRepository<ContentDetails, UUID> {
    List<ContentDetails> findAllByIdIn(List<UUID> ids);
}
