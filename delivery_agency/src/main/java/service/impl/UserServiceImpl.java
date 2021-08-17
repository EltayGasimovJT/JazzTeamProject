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

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public User save(UserDto userDto) throws SQLException {
        User user = User.builder().id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .build();
        if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            user.setWorkingPlace(modelMapper.map(userDto.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            user.setWorkingPlace(modelMapper.map(userDto.getWorkingPlace(), Warehouse.class));
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User update(UserDto userDto) throws SQLException {
        User user = UserMapper.toUser(userDto);
        return userRepository.update(user);
    }

    @Override
    public User changeWorkingPlace(UserDto userToUpdate, AbstractBuildingDto newWorkingPlace) throws SQLException {
        userToUpdate.setWorkingPlace(newWorkingPlace);

        return update(userToUpdate);
    }
}