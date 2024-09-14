package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Users;
import src.Repositories.UsersRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserService(
            UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
    }

    public Users addUser(Long telegramId) {
        var user = new Users();
        user.setTelegramId(telegramId);

        return usersRepository.save(user);
    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    public void deleteUser(UUID userId) {
        var user = usersRepository.findById(userId);
        user.ifPresent(users -> usersRepository.delete(users));
    }

    public void deleteUser(Long telegramId) {
        var user = usersRepository.findByTelegramId(telegramId);
        user.ifPresent(users -> usersRepository.delete(users));
    }

    public Users getUser(Long telegramId) {
        return usersRepository.findByTelegramId(telegramId).orElseThrow();
    }

    public Optional<Users> getUser(UUID id) {
        return usersRepository.findById(id);
    }
}
