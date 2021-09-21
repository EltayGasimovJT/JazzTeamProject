package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.Role;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.PROCESSING_POINT;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    @Disabled("Transactional error")
    void addUser() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto expected = WorkerDto
                .builder()
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(orderProcessingPointDtoToTest)
                .build();

        Worker actual = userService.save(expected);
        expected.setId(actual.getId());
        expected.setId(expected.getId());

        Assertions.assertEquals(CustomModelMapper.mapDtoToWorker(expected), actual);
    }

    @Test
    @Disabled("Transactional error")
    void deleteUser() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto firstUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
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
    @Disabled("Transactional error")
    void findAllUsers() {
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        WorkerDto firstUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto secondUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
                .workingPlace(orderProcessingPointDtoToTest)
                .build();
        WorkerDto thirdUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_WAREHOUSE_WORKER)
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
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(orderProcessingPointDtoToTest)
                .name(expected)
                .build();

        String actual = userService.update(newUser).getName();

        Assertions.assertEquals(expected, actual);
    }

}
