package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jazzteam.eltay.gasimov.repository.UserRepository;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.jazzteam.eltay.gasimov.validator.UserValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User save(UserDto userDtoToSave) throws SQLException {
        User userToSave = User.builder().id(userDtoToSave.getId())
                .name(userDtoToSave.getName())
                .surname(userDtoToSave.getSurname())
                .roles(userDtoToSave.getRoles())
                .build();
        if (userDtoToSave.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            userToSave.setWorkingPlace(modelMapper.map(userDtoToSave.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDtoToSave.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            userToSave.setWorkingPlace(modelMapper.map(userDtoToSave.getWorkingPlace(), Warehouse.class));
        }

        UserValidator.validateOnSave(userToSave);

        return userRepository.save(userToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<User> foundClientFromRepository = userRepository.findById(idForDelete);
        User orderToUpdate = foundClientFromRepository.orElseGet(User::new);
        UserValidator.validateUser(orderToUpdate);
        userRepository.deleteById(idForDelete);
    }

    @Override
    public List<User> findAll() throws IllegalArgumentException {
        List<User> usersFromRepository = userRepository.findAll();
        UserValidator.validateUsersList(usersFromRepository);
        return usersFromRepository;
    }

    @Override
    public User findOne(long idForSearch) throws IllegalArgumentException {
        Optional<User> foundClientFromRepository = userRepository.findById(idForSearch);
        User foundUser = foundClientFromRepository.orElseGet(User::new);
        UserValidator.validateUser(foundUser);
        return foundUser;
    }

    @Override
    public User update(UserDto userDtoToUpdate) throws SQLException {
        User userToUpdate = CustomModelMapper.mapDtoToUser(userDtoToUpdate);
        UserValidator.validateUser(userToUpdate);
        return userRepository.save(userToUpdate);
    }

    @Override
    public User changeWorkingPlace(UserDto userToUpdate, AbstractBuildingDto newWorkingPlace) throws SQLException, IllegalArgumentException {
        userToUpdate.setWorkingPlace(newWorkingPlace);

        return update(userToUpdate);
    }
}