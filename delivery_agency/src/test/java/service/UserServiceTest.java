package service;

import dto.OrderProcessingPointDto;
import dto.UserDto;
import dto.WarehouseDto;
import entity.User;
import lombok.SneakyThrows;
import mapping.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import service.impl.OrderProcessingPointServiceImpl;
import service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static dto.WorkingPlaceType.PROCESSING_POINT;
import static dto.WorkingPlaceType.WAREHOUSE;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();
    private final ModelMapper userMapper = new ModelMapper();
    private final OrderProcessingPointService processingPointService = new OrderProcessingPointServiceImpl();

    @Test
    void addUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);
        UserDto expected = UserDto
                .builder()
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .roles(Arrays.asList("User", "Client"))
                .workingPlace(orderProcessingPointDtoToSave)
                .build();

        UserDto actual = UserMapper.toDto(userService.save(expected));

        Assertions.assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto secondUser = UserDto
                .builder()
                .id(2L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        userService.deleteUser(firstUser.getId());

        List<User> allUsers = userService.findAllUsers();
        int unexpected = 3;

        int actual = allUsers.size();

        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void findAllUsers() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);

        UserDto firstUser = UserDto.builder()
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto secondUser = UserDto.builder()
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto thirdUser = UserDto.builder()
                .workingPlace(orderProcessingPointDtoToSave)
                .build();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        List<User> allUsers = userService.findAllUsers();

        int expected = 3;

        int actual = allUsers.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto expected = UserDto
                .builder()
                .id(2L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .workingPlace(orderProcessingPointDtoToSave)
                .build();

        userService.save(firstUser);
        userService.save(expected);
        userService.save(thirdUser);

        UserDto actual = UserMapper.toDto(userService.getUser(2));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);
        UserDto user = UserDto
                .builder()
                .id(1L)
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToSave)
                .build();
        userService.save(user);

        String expected = "Victor";

        orderProcessingPointDtoToSave.setId(2L);
        UserDto newUser = UserDto
                .builder()
                .id(1L)
                .workingPlace(orderProcessingPointDtoToSave)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void changeWorkingPlace() throws SQLException {
        OrderProcessingPointDto workingPlace = new OrderProcessingPointDto();
        workingPlace.setLocation("Minsk");
        workingPlace.setWorkingPlaceType(PROCESSING_POINT);
        UserDto user = UserDto
                .builder()
                .id(1L)
                .workingPlace(workingPlace)
                .name("Vlad")
                .build();

        userService.save(user);

        String expected = workingPlace.getLocation();
        workingPlace.setLocation("Polotsk");
        workingPlace.setWorkingPlaceType(WAREHOUSE);


        userService.changeWorkingPlace(UserMapper.toDto(userService.getUser(user.getId())), workingPlace);

        String actual = userService.getUser(user.getId())
                .getWorkingPlace()
                .getLocation();

        Assertions.assertNotEquals(expected, actual);
    }
}
