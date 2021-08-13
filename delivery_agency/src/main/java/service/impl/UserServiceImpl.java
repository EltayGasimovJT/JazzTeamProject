package service.impl;

import dto.AbstractBuildingDto;
import dto.UserDto;
import entity.AbstractBuilding;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.*;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserDto addUser(UserDto user) {
        User save = userRepository.save(fromDtoToUser(user));
        return fromUserToDto(save);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> resultUsers = new ArrayList<>();
        for (User user : users) {
            resultUsers.add(fromUserToDto(user));
        }
        return resultUsers;
    }

    @Override
    public UserDto getUser(long id) {
        return fromUserToDto(userRepository.findOne(id));
    }

    @Override
    public UserDto update(UserDto user) throws SQLException {
        return fromUserToDto(userRepository.update(fromDtoToUser(user)));
    }

    @Override
    public UserDto changeWorkingPlace(UserDto userToUpdate, AbstractBuildingDto newWorkingPlace) {
        User user = User.builder()
                .id(userToUpdate.getId())
                .name(userToUpdate.getName())
                .surname(userToUpdate.getSurname())
                .roles(userToUpdate.getRoles())
                .workingPlace(fromDtoToAbstractBuilding(newWorkingPlace))
                .build();

        return fromUserToDto(userRepository.update(user));
    }
}