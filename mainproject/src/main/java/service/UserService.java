package service;

import entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUser(User user);

    List<User> showUsers();

    User getUser(long id);

    User update(User user);
}
