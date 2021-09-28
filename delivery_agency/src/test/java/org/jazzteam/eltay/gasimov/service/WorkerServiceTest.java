package org.jazzteam.eltay.gasimov.service;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.PROCESSING_POINT;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WorkerServiceTest {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkerRolesService workerRolesService;

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
                .password("qweqw")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        Worker actual = workerService.save(expected);
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
                .password("wqewe")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        Worker expected = workerService.save(expectedDto);
        Worker actual = workerService.findOne(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByPassword() {
        WorkerRolesDto rolesDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        modelMapper.map(workerRolesService.save(rolesDto), WorkerRolesDto.class);
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
                .password("Eltay1")
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        RegistrationRequest expected = RegistrationRequest.builder()
                .login(expectedDto.getName())
                .surname(expectedDto.getSurname())
                .password("Eltay1")
                .role(Role.ROLE_ADMIN.name())
                .workingPlaceId(savedProcessingPoint.getId())
                .workingPlaceType(PROCESSING_POINT.name())
                .build();
        Worker savedWorker = workerService.saveForRegistration(expected);
        expectedDto.setId(savedWorker.getId());
        WorkerDto actual = CustomModelMapper.mapWorkerToDto(workerService.findByPassword(savedWorker.getPassword()));
        expectedDto.setPassword(savedWorker.getPassword());
        Assertions.assertEquals(expectedDto, actual);
    }

    @Test
    void findByLoginAndPassword() {
        WorkerRolesDto rolesDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        modelMapper.map(workerRolesService.save(rolesDto), WorkerRolesDto.class);
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
                .password("Eltay1")
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        RegistrationRequest expected = RegistrationRequest.builder()
                .login(expectedDto.getName())
                .surname(expectedDto.getSurname())
                .password("Eltay1")
                .role(Role.ROLE_ADMIN.name())
                .workingPlaceId(savedProcessingPoint.getId())
                .workingPlaceType(PROCESSING_POINT.name())
                .build();
        Worker savedWorker = workerService.saveForRegistration(expected);
        expectedDto.setId(savedWorker.getId());
        WorkerDto actual = CustomModelMapper.mapWorkerToDto(workerService.findByLoginAndPassword(expectedDto.getName(), expectedDto.getPassword()));
        expectedDto.setPassword(savedWorker.getPassword());
        Assertions.assertEquals(expectedDto, actual);
    }

    @Test
    void saveForRegistrationTest() {
        WorkerRolesDto rolesDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        modelMapper.map(workerRolesService.save(rolesDto), WorkerRolesDto.class);
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
        RegistrationRequest expected = RegistrationRequest.builder()
                .login(expectedDto.getName())
                .surname(expectedDto.getSurname())
                .password("Eltay1")
                .role(Role.ROLE_ADMIN.name())
                .workingPlaceId(savedProcessingPoint.getId())
                .workingPlaceType(PROCESSING_POINT.name())
                .build();
        Worker actual = workerService.saveForRegistration(expected);
        expectedDto.setId(actual.getId());
        expectedDto.setPassword(actual.getPassword());
        Assertions.assertEquals(expectedDto, CustomModelMapper.mapWorkerToDto(actual));
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
                .password("wqeqw")
                .role(Role.ROLE_ADMIN)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto secondUser = WorkerDto
                .builder()
                .name("Vlad")
                .password("q1241q")
                .surname("Vlad")
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto thirdUser = WorkerDto
                .builder()
                .name("Vlad")
                .surname("Vlad")
                .password("124wqrsa")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();

        Worker savedFirst = workerService.save(firstUser);
        Worker savedSecond = workerService.save(secondUser);
        Worker savedThird = workerService.save(thirdUser);

        workerService.delete(savedFirst.getId());

        List<Worker> actual = workerService.findAll();
        Assertions.assertEquals(Arrays.asList(savedSecond, savedThird), actual);
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
                .password("qwr1wq3521")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto secondUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .password("qwqt121tqsfa")
                .role(Role.ROLE_PROCESSING_POINT_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        WorkerDto thirdUser = WorkerDto.builder()
                .name("Vlad")
                .surname("Vlad")
                .password("asfqwr12r42")
                .role(Role.ROLE_WAREHOUSE_WORKER)
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();

        Worker savedFirst = workerService.save(firstUser);
        Worker savedSecond = workerService.save(secondUser);
        Worker savedThird = workerService.save(thirdUser);

        List<Worker> actual = workerService.findAll();

        Assertions.assertEquals(Arrays.asList(savedFirst, savedSecond, savedThird), actual);
    }

    @Test
    void update() {
        WorkerRolesDto rolesDto = WorkerRolesDto.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
        modelMapper.map(workerRolesService.save(rolesDto), WorkerRolesDto.class);
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
        RegistrationRequest expected = RegistrationRequest.builder()
                .login(expectedDto.getName())
                .surname(expectedDto.getSurname())
                .password("Eltay1")
                .role(Role.ROLE_ADMIN.name())
                .workingPlaceId(savedProcessingPoint.getId())
                .workingPlaceType(PROCESSING_POINT.name())
                .build();
        Worker savedWorker = workerService.saveForRegistration(expected);
        expectedDto.setId(savedWorker.getId());
        expectedDto.setPassword(savedWorker.getPassword());
        String newName = "Victor";

        savedWorker.setName(newName);

        Worker actual = workerService.update(CustomModelMapper.mapWorkerToDto(savedWorker));

        Assertions.assertEquals(savedWorker, actual);
    }
}