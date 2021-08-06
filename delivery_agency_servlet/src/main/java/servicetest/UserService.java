package servicetest;

import entity.AbstractBuilding;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User addUser(User user) throws SQLException;

    void deleteUser(User user);

    List<User> findAllUsers() throws SQLException;

    User getUser(long id) throws SQLException;

    User update(User user) throws SQLException;

    User changeWorkingPlace(User userId, AbstractBuilding newWorkingPlace) throws SQLException;
}
