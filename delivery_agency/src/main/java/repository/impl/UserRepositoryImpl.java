package repository.impl;

import entity.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        users.remove(findOne(id));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findOne(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User update(User update) {
        User actual = findOne(update.getId());
        users.remove(actual);
        actual.setName(update.getName());
        actual.setSurname(update.getSurname());
        actual.setWorkingPlace(update.getWorkingPlace());
        actual.setRoles(update.getRoles());
        users.add(actual);
        return actual;
    }
}