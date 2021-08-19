package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User save(UserDto userToSave) throws SQLException;

    void delete(Long idForDelete);

    List<User> findAll() throws SQLException;

    User findOne(long idForSearch) throws SQLException;

    User update(UserDto userDtoToUpdate) throws SQLException;

    User changeWorkingPlace(UserDto userId, AbstractBuildingDto newWorkingPlace) throws SQLException;

    void clear();

}