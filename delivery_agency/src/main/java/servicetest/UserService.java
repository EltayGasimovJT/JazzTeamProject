package servicetest;

import entity.AbstractBuilding;
import entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUser(User user);

    List<User> findAllUsers();

    User getUser(long id);

    User update(User user);

    User changeWorkingPlace(User userId, AbstractBuilding newWorkingPlace);
}
