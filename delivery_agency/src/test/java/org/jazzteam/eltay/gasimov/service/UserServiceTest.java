/*
package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.PROCESSING_POINT;
import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.WAREHOUSE;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void addUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        UserDto expected = UserDto
                .builder()
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .roles(Arrays.asList("User", "Client"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        userService.clear();

        UserDto actual = CustomModelMapper.mapUserToDto(userService.save(expected));

        Assertions.assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto secondUser = UserDto
                .builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        userService.delete(firstUser.getId());

        List<User> allUsers = userService.findAll();
        int unexpected = 3;

        int actual = allUsers.size();

        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void findAllUsers() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);

        UserDto firstUser = UserDto.builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto secondUser = UserDto.builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto thirdUser = UserDto.builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        List<User> allUsers = userService.findAll();

        int expected = 3;

        int actual = allUsers.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        UserDto firstUser = UserDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto expected = UserDto
                .builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        UserDto thirdUser = UserDto
                .builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(expected);
        userService.save(thirdUser);

        UserDto actual = CustomModelMapper.mapUserToDto(userService.findOne(2));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        UserDto user = UserDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(user);

        String expected = "Victor";

        orderProcessingPointDtoToTest.setId(2L);
        UserDto newUser = UserDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void changeWorkingPlace() throws SQLException {
        OrderProcessingPointDto workingPlaceToTest = new OrderProcessingPointDto();
        workingPlaceToTest.setLocation("Minsk");
        workingPlaceToTest.setWorkingPlaceType(PROCESSING_POINT);
        UserDto user = UserDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("user", "Admin"))
                .workingPlace(workingPlaceToTest)
                .build();
        userService.clear();

        userService.save(user);

        String expected = workingPlaceToTest.getLocation();
        workingPlaceToTest.setLocation("Polotsk");
        workingPlaceToTest.setWorkingPlaceType(WAREHOUSE);


        userService.changeWorkingPlace(CustomModelMapper.mapUserToDto(userService.findOne(user.getId())), workingPlaceToTest);

        String actual = userService.findOne(user.getId())
                .getWorkingPlace()
                .getLocation();

        Assertions.assertNotEquals(expected, actual);
    }
}
*/
