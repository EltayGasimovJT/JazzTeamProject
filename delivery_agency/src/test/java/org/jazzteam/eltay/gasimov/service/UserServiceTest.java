/*
package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Worker;
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
    private WorkerService userService;

    @Test
    void addUser() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto expected = WorkerDto
                .builder()
                .id(1L)
                .name("Igor")
                .surname("Igor")
                .roles(Arrays.asList("Worker", "Client"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        userService.clear();

        WorkerDto actual = CustomModelMapper.mapUserToDto(userService.save(expected));

        Assertions.assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto firstUser = WorkerDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto
                .builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        userService.delete(firstUser.getId());

        List<Worker> allUsers = userService.findAll();
        int unexpected = 3;

        int actual = allUsers.size();

        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void findAllUsers() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);

        WorkerDto firstUser = WorkerDto.builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto.builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto.builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        List<Worker> allUsers = userService.findAll();

        int expected = 3;

        int actual = allUsers.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWorker() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto firstUser = WorkerDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto expected = WorkerDto
                .builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(firstUser);
        userService.save(expected);
        userService.save(thirdUser);

        WorkerDto actual = CustomModelMapper.mapUserToDto(userService.findOne(2));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto worker = WorkerDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        userService.clear();

        userService.save(worker);

        String expected = "Victor";

        orderProcessingPointDtoToTest.setId(2L);
        WorkerDto newUser = WorkerDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
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
        WorkerDto worker = WorkerDto
                .builder()
                .id(1L)
                .name("Vlad")
                .surname("Vlad")
                .roles(Arrays.asList("worker", "Admin"))
                .workingPlace(workingPlaceToTest)
                .build();
        userService.clear();

        userService.save(worker);

        String expected = workingPlaceToTest.getLocation();
        workingPlaceToTest.setLocation("Polotsk");
        workingPlaceToTest.setWorkingPlaceType(WAREHOUSE);


        userService.changeWorkingPlace(CustomModelMapper.mapUserToDto(userService.findOne(worker.getId())), workingPlaceToTest);

        String actual = userService.findOne(worker.getId())
                .getWorkingPlace()
                .getLocation();

        Assertions.assertNotEquals(expected, actual);
    }
}
*/
