package servicetest.impl;

import entity.AbstractBuilding;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import servicetest.UserService;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public User changeWorkingPlace(User userToUpdate, AbstractBuilding newWorkingPlace) {
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
