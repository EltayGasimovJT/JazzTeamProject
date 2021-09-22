package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Test
    void addUser() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto expected = WorkerDto
                .builder()
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        Worker actual = userService.save(expected);
        expected.setId(actual.getId());

        Assertions.assertEquals(CustomModelMapper.mapDtoToWorker(expected), actual);
    }

    @Test
    void findOne() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto expectedDto = WorkerDto
                .builder()
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        Worker expected = userService.save(expectedDto);
        Worker actual = userService.findOne(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void changeWorkingPlace() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        WarehouseDto newWorkingPlace = new WarehouseDto();
        newWorkingPlace.setLocation("Moscow");
        newWorkingPlace.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        Warehouse newPlaceSaved = warehouseService.save(newWorkingPlace);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto expectedDto = WorkerDto
                .builder()
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        Worker expected = userService.save(expectedDto);
        Worker actual = userService.changeWorkingPlace(expected.getId(), newPlaceSaved.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteUser() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto firstUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto secondUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
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
    void findAllUsers() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setId(1L);
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto firstUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_ADMIN)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto secondUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto thirdUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
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
