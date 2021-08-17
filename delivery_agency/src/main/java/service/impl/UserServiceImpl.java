package service.impl;

import dto.AbstractBuildingDto;
import dto.UserDto;
import dto.WorkingPlaceType;
import entity.OrderProcessingPoint;
import entity.User;
import entity.Warehouse;
import lombok.extern.slf4j.Slf4j;
import mapping.UserMapper;
import org.modelmapper.ModelMapper;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;
import validator.UserValidator;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();

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
        UserValidator.validateUser(userRepository.findOne(idForDelete));
        userRepository.delete(idForDelete);
    }

    @Override
    public List<User> findAll() throws IllegalArgumentException {
        List<User> usersFromRepository = userRepository.findAll();
        UserValidator.validateUsersList(usersFromRepository);
        return usersFromRepository;
    }

    @Override
    public User findOne(long idForSearch) throws IllegalArgumentException {
        User foundUser = userRepository.findOne(idForSearch);
        UserValidator.validateUser(foundUser);
        return foundUser;
    }

    @Override
    public User update(UserDto userDtoToUpdate) throws SQLException {
        User userToUpdate = UserMapper.toUser(userDtoToUpdate);
        UserValidator.validateUser(userToUpdate);
        return userRepository.update(userToUpdate);
    }

    @Override
    public User changeWorkingPlace(UserDto userToUpdate, AbstractBuildingDto newWorkingPlace) throws SQLException, IllegalArgumentException {
        userToUpdate.setWorkingPlace(newWorkingPlace);

        return update(userToUpdate);
    }
}