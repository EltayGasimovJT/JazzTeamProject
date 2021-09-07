package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.UserRolesDto;
import org.jazzteam.eltay.gasimov.entity.UserRoles;

import java.util.List;

public interface UserRolesService {
    UserRoles save(UserRolesDto userRolesDtoToSave);

    void delete(Long idForDelete);

    List<UserRoles> findAll();

    UserRoles findOne(long idForSearch);

    UserRoles update(UserRolesDto userRolesDtoToUpdate);
}
