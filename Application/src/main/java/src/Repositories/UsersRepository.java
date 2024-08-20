package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}