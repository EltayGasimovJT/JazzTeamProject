package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.entity.UserRoles;

import java.sql.SQLException;
import java.util.List;

public interface UserRolesService {
    UserRoles save(UserRolesDto userRolesDtoToSave) throws SQLException;

    void delete(Long idForDelete);

    List<UserRoles> findAll() throws SQLException;

    UserRoles findOne(long idForSearch) throws SQLException;

    UserRoles update(UserRolesDto userRolesDtoToUpdate) throws SQLException;
}
