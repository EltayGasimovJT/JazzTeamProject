package service.impl;

import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User addUser(User user) {
        userRepository.save(user);
        return null;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> showUsers() {
        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
