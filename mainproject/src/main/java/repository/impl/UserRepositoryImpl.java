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
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findOne(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User update(User update) {
        for (User user : users) {
            if (user.getId() == update.getId()) {
                user.setName(update.getName());
                user.setSurname(update.getSurname());
                user.setWorkingPlace(update.getWorkingPlace());
                user.setRoles(update.getRoles());
            }
        }
        return update;
    }
}
