package service;

import dto.AbstractBuildingDto;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User addUser(UserDto user) throws SQLException;

    void deleteUser(Long id);

    List<User> findAllUsers() throws SQLException;

    User getUser(long id) throws SQLException;

    User update(UserDto user) throws SQLException;

    User changeWorkingPlace(UserDto userId, AbstractBuildingDto newWorkingPlace) throws SQLException;
}