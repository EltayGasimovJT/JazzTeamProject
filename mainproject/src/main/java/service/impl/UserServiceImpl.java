package service.impl;

import entity.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User addUser(User user) {
        userRepository.save(user);
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public List<User> showUsers() {
        for (User user : userRepository.findAll()) {
            System.out.println(user);
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
