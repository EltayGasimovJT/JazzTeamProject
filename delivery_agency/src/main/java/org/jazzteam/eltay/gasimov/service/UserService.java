package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;

import java.util.List;

public interface UserService {
    User save(UserDto userToSave);

    void delete(Long idForDelete);

    List<User> findAll();

    User findOne(long idForSearch);

    User update(UserDto userDtoToUpdate);

    User changeWorkingPlace(Long userId, AbstractBuildingDto newWorkingPlace);

    User findByName(String name);

    User findByLoginAndPassword(String login, String password);

    User findByPassword(String password);

    User saveForRegistration(RegistrationRequest registrationRequest);
}