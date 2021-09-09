package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.AbstractBuilding;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.UserRepository;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.jazzteam.eltay.gasimov.service.UserRolesService;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.jazzteam.eltay.gasimov.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private UserRolesService userRolesService;

    @Override
    public User save(UserDto userDtoToSave) {
        User userToSave = CustomModelMapper.mapDtoToUser(userDtoToSave);
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
        Optional<User> foundWorkerFromRepository = userRepository.findById(idForSearch);
        User foundUser = foundWorkerFromRepository.orElseGet(User::new);
        UserValidator.validateUser(foundUser);
        return foundUser;
    }

    @Override
    public User update(UserDto userDtoToUpdate) {
        User userToUpdate = CustomModelMapper.mapDtoToUser(userDtoToUpdate);
        UserValidator.validateUser(userToUpdate);
        return userRepository.save(userToUpdate);
    }

    @Override
    public User changeWorkingPlace(Long id, AbstractBuildingDto newWorkingPlace) throws IllegalArgumentException {
        Optional<User> foundUser = userRepository.findById(id);
        User userFromOptional = foundUser.orElseGet(User::new);

        if (newWorkingPlace instanceof OrderProcessingPointDto) {
            AbstractBuilding abstractBuildingToUpdate = new OrderProcessingPoint();
            abstractBuildingToUpdate.setId(newWorkingPlace.getId());
            abstractBuildingToUpdate.setLocation(newWorkingPlace.getLocation());
            abstractBuildingToUpdate.setWorkingPlaceType(newWorkingPlace.getWorkingPlaceType().toString());
            userFromOptional.setWorkingPlace(abstractBuildingToUpdate);
        } else if (newWorkingPlace instanceof WarehouseDto) {
            AbstractBuilding abstractBuildingToUpdate = new Warehouse();
            abstractBuildingToUpdate.setId(newWorkingPlace.getId());
            abstractBuildingToUpdate.setLocation(newWorkingPlace.getLocation());
            abstractBuildingToUpdate.setWorkingPlaceType(newWorkingPlace.getWorkingPlaceType().toString());
            userFromOptional.setWorkingPlace(abstractBuildingToUpdate);
        }

        return update(CustomModelMapper.mapUserToDto(userFromOptional));
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
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public User findByPassword(String password) {
        Optional<User> foundClientFromRepository = userRepository.findByPassword(password);
        User foundUser = foundClientFromRepository.orElseGet(User::new);
        UserValidator.validateUser(foundUser);
        return foundUser;
    }

    @Override
    public User saveForRegistration(RegistrationRequest registrationRequest) {
        User userToSave = User.builder()
                .name(registrationRequest.getLogin())
                .surname(registrationRequest.getSurname())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(
                        Stream.of(userRolesService.findByRole(registrationRequest.getRole()))
                        .collect(Collectors.toSet())
                )
                .workingPlace(processingPointService.findOne(registrationRequest.getWorkingPlaceId()))
                .build();
        return userRepository.save(userToSave);
    }
}