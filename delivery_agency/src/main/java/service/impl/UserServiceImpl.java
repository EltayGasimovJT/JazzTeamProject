package service.impl;

import dto.AbstractBuildingDto;
import dto.UserDto;
import dto.WorkingPlaceType;
import entity.AbstractBuilding;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.OrderProcessingPointService;
import service.UserService;
import service.WarehouseService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final OrderProcessingPointService processingPointService = new OrderProcessingPointServiceImpl();
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Override
    public User addUser(UserDto userDto) throws SQLException {
        User user = User.builder().id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .build();
        if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            AbstractBuilding abstractBuilding = processingPointService.findOne(userDto.getWorkingPlace().getId());
            user.setWorkingPlace(abstractBuilding);

        } else if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            AbstractBuilding abstractBuilding = warehouseService.findOne(userDto.getWorkingPlace().getId());
            user.setWorkingPlace(abstractBuilding);
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
        User user = User.builder().id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .build();

        if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            AbstractBuilding abstractBuilding = processingPointService.findOne(userDto.getWorkingPlace().getId());
            user.setWorkingPlace(abstractBuilding);

        } else if (userDto.getWorkingPlace().getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            AbstractBuilding abstractBuilding = warehouseService.findOne(userDto.getWorkingPlace().getId());
            user.setWorkingPlace(abstractBuilding);
        }
        return userRepository.update(user);
    }

    @Override
    public User changeWorkingPlace(UserDto userToUpdate, AbstractBuildingDto newWorkingPlace) throws SQLException {
        userToUpdate.setWorkingPlace(newWorkingPlace);

        return update(userToUpdate);
    }
}