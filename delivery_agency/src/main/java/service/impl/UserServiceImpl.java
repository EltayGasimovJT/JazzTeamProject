package service.impl;

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
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(fromUserToDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto getUser(long id) {
        return fromUserToDto(userRepository.findOne(id));
    }

    @Override
    public UserDto update(UserDto user) throws SQLException {
        User update = userRepository.update(fromDtoToUser(user));
        return fromUserToDto(update);
    }

    @Override
    public UserDto changeWorkingPlace(UserDto userToUpdate, AbstractBuilding newWorkingPlace) {
        User user = User.builder()
                .id(userToUpdate.getId())
                .name(userToUpdate.getName())
                .surname(userToUpdate.getSurname())
                .roles(userToUpdate.getRoles())
                .workingPlace(newWorkingPlace)
                .build();
        User update = userRepository.update(user);

        return fromUserToDto(update);
    }

    private UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .roles(user.getRoles())
                .workingPlace(user.getWorkingPlace())
                .build();
    }

    private User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .workingPlace(userDto.getWorkingPlace())
                .build();
    }
}