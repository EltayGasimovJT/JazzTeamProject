package service;

import dto.OrderProcessingPointDto;
import dto.UserDto;
import dto.WarehouseDto;
import entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import service.impl.OrderProcessingPointServiceImpl;
import service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static dto.WorkingPlaceType.PROCESSING_POINT;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();
    private final ModelMapper userMapper = new ModelMapper();
    private final OrderProcessingPointService processingPointService = new OrderProcessingPointServiceImpl();

    @Test
    void addUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToSave = new OrderProcessingPointDto();
        orderProcessingPointDtoToSave.setId(1L);
        orderProcessingPointDtoToSave.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToSave.setWarehouse(new WarehouseDto());
        UserDto expected = UserDto
                .builder()
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .roles(Arrays.asList("User", "Client"))
                .workingPlace(orderProcessingPointDtoToSave)
                .build();

        UserDto actual = userMapper.map(userService.addUser(expected), UserDto.class);


        Assertions.assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .build();
        UserDto secondUser = UserDto
                .builder()
                .id(2L)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .build();

        userService.addUser(firstUser);
        userService.addUser(secondUser);
        userService.addUser(thirdUser);

        userService.deleteUser(firstUser.getId());

        List<User> allUsers = userService.findAllUsers();
        int unexpected = 3;

        int actual = allUsers.size();

        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void findAllUsers() throws SQLException {
        UserDto firstUser = UserDto.builder().build();
        UserDto secondUser = UserDto.builder().build();
        UserDto thirdUser = UserDto.builder().build();

        userService.addUser(firstUser);
        userService.addUser(secondUser);
        userService.addUser(thirdUser);

        List<User> allUsers = userService.findAllUsers();

        int expected = 3;

        int actual = allUsers.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUser() throws SQLException {
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .build();
        UserDto expected = UserDto
                .builder()
                .id(2L)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .build();
        userService.addUser(firstUser);
        userService.addUser(expected);
        userService.addUser(thirdUser);

        User actual = userService.getUser(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        UserDto user = UserDto
                .builder()
                .id(1L)
                .name("Vlad")
                .build();
        userService.addUser(user);

        String expected = "Victor";
        UserDto newUser = UserDto
                .builder()
                .id(1L)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void changeWorkingPlace() throws SQLException {
        OrderProcessingPointDto workingPlace = new OrderProcessingPointDto();
        workingPlace.setLocation("Minsk");
        workingPlace.setWarehouse(new WarehouseDto());
        UserDto user = UserDto
                .builder()
                .id(1L)
                .workingPlace(workingPlace)
                .name("Vlad")
                .build();

        userService.addUser(user);

        String expected = workingPlace.getLocation();
        workingPlace.setWarehouse(new WarehouseDto());
        workingPlace.setLocation("Polotsk");

        userService.changeWorkingPlace(userMapper.map(userService.getUser(user.getId()), UserDto.class), workingPlace);

        String actual = userService.getUser(user.getId())
                .getWorkingPlace()
                .getLocation();

        Assertions.assertNotEquals(expected, actual);
    }
}
