package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.entity.UserRoles;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserRolesServiceImpl implements UserRolesService {
    @Override
    public UserRoles save(UserRolesDto userRolesDtoToSave) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long idForDelete) {

    }

    @Override
    public List<UserRoles> findAll() throws SQLException {
        return null;
    }

    @Override
    public UserRoles findOne(long idForSearch) throws SQLException {
        return null;
    }

    @Override
    public UserRoles update(UserRolesDto userRolesDtoToUpdate) throws SQLException {
        return null;
    }
}
