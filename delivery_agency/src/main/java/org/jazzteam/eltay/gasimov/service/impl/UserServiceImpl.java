package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.UserRepository;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.jazzteam.eltay.gasimov.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRolesService userRolesService;

    @Override
    public User save(UserDto userDtoToSave) throws SQLException {
        User userToSave = User.builder().id(userDtoToSave.getId())
                .name(userDtoToSave.getName())
                .surname(userDtoToSave.getSurname())
                .password(passwordEncoder.encode(userDtoToSave.getPassword()))
                .roles(Collections.singletonList(userRolesService.findOne(3)))
                .build();
        if (userDtoToSave.getWorkingPlace().equals(WorkingPlaceType.PROCESSING_POINT)) {
            userToSave.setWorkingPlace(modelMapper.map(userDtoToSave.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDtoToSave.getWorkingPlace().equals(WorkingPlaceType.WAREHOUSE)) {
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
        if (newWorkingPlace instanceof OrderProcessingPointDto) {
            userToUpdate.setWorkingPlace(WorkingPlaceType.PROCESSING_POINT);
        } else if (newWorkingPlace instanceof WarehouseDto) {
            userToUpdate.setWorkingPlace(WorkingPlaceType.WAREHOUSE);
        }

        return update(userToUpdate);
    }

    @Override
    public User findByName(String name) {
        Optional<User> foundClientFromRepository = userRepository.findByName(name);
        User foundUser = foundClientFromRepository.orElseGet(User::new);
        UserValidator.validateUser(foundUser);
        return foundUser;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByName(login);
        if (userEntity != null) {
            log.info("u");
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return userEntity;
    }
}