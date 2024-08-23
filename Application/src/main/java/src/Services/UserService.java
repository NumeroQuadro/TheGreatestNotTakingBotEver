package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Users;
import src.Repositories.UsersRepository;

@Service
public class UserService {
    private UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users addNewUser(Long telegramId, String telegramTag) {
        var user = new Users();
        user.setTelegramId(telegramId);
        user.setTelegramTag(telegramTag);

        return usersRepository.save(user);
    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    public void deleteUser(Integer userId) {
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

    public Users getUser(Integer id) {
        return usersRepository.findById(id).orElseThrow();
    }
}
