package service.impl;

import entity.AbstractBuilding;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User addUser(User user) throws SQLException {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) throws SQLException {
        return userRepository.findOne(id);
    }

    @Override
    public User update(User user) throws SQLException {
        return userRepository.update(user);
    }

    @Override
    public User changeWorkingPlace(User userToUpdate, AbstractBuilding newWorkingPlace) throws SQLException {
        User user = User.builder()
                .id(userToUpdate.getId())
                .name(userToUpdate.getName())
                .surname(userToUpdate.getSurname())
                .roles(userToUpdate.getRoles())
                .workingPlace(newWorkingPlace)
                .build();
        return userRepository.update(user);
    }
}
