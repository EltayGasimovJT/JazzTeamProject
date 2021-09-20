package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.controller.security.model.RegistrationRequest;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.dto.WorkerRolesDto;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.PROCESSING_POINT;
import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.WAREHOUSE;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WorkerServiceTest {
    @Autowired
    private WorkerService userService;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private WorkerRolesService workerRolesService;

    @Test
    void addUser() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto expected = WorkerDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        Worker actual = userService.save(expected);
        expected.setId(expected.getId());

        Assertions.assertEquals(CustomModelMapper.mapDtoToWorker(expected), actual);
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
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto
                .builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        Worker savedWorker = userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        userService.delete(savedWorker.getId());

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
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto.builder()
                .id(2L)
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto.builder()
                .id(3L)
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        userService.save(firstUser);
        userService.save(secondUser);
        userService.save(thirdUser);

        List<Worker> allUsers = userService.findAll();

        int expected = 3;

        int actual = allUsers.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto worker = WorkerDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        userService.save(worker);

        String expected = "Victor";

        orderProcessingPointDtoToTest.setId(2L);
        WorkerDto newUser = WorkerDto
                .builder()
                .id(1L)
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assertions.assertEquals(expected, actual);
    }

}
