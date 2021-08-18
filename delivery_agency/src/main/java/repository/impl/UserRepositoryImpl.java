package repository.impl;

import entity.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User userToSave) {
        users.add(userToSave);
        return userToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        users.remove(findOne(idForDelete));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findOne(Long idForSearch) {
        return users.stream()
                .filter(user -> user.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User update(User newUser) {
        User userToUpdate = findOne(newUser.getId());
        users.remove(userToUpdate);
        userToUpdate.setName(newUser.getName());
        userToUpdate.setSurname(newUser.getSurname());
        userToUpdate.setWorkingPlace(newUser.getWorkingPlace());
        userToUpdate.setRoles(newUser.getRoles());
        users.add(userToUpdate);
        return userToUpdate;
    }
}