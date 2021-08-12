package service;

import dto.UserDto;
import entity.AbstractBuilding;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserDto addUser(UserDto user) throws SQLException;

    void deleteUser(UserDto user);

    List<UserDto> findAllUsers() throws SQLException;

    UserDto getUser(long id) throws SQLException;

    UserDto update(UserDto user) throws SQLException;

    UserDto changeWorkingPlace(UserDto userId, AbstractBuilding newWorkingPlace) throws SQLException;
}
