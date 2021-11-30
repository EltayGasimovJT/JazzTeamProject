package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderHistoryDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.dto.WorkerDto;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.entity.WorkingPlaceType.PROCESSING_POINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderHistoryServiceTest {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void save() {
        Worker savedWorker = getSavedWorker();
        OrderHistoryDto expected = OrderHistoryDto
                .builder()
                .comment("Comment")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_THE_WAY_TO_ACCEPTED)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistoryDto actual = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(expected));
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Worker savedWorker = getSavedWorker();
        OrderHistoryDto expected = OrderHistoryDto
                .builder()
                .comment("Comment")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_THE_WAY_TO_ACCEPTED)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistoryDto toDelete = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(expected));
        OrderHistoryDto expectedHistory = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(expected));
        orderHistoryService.delete(toDelete.getId());

        List<OrderHistoryDto> actual = orderHistoryService.findAll().stream()
                .map(CustomModelMapper::mapHistoryToDto)
                .collect(Collectors.toList());
        assertEquals(Collections.singletonList(expectedHistory), actual);
    }

    @Test
    void findAll() {
        Worker savedWorker = getSavedWorker();
        OrderHistoryDto firstToSave = OrderHistoryDto
                .builder()
                .comment("Comment")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_THE_WAY_TO_ACCEPTED)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistoryDto secondToSave = OrderHistoryDto
                .builder()
                .comment("Comment1")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_TH_FINAL_WAREHOUSE_TO_THE_ON_THE_WAY_TO_THE_PROCESSING_POINT)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistoryDto firstSaved = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(firstToSave));
        OrderHistoryDto secondSaved = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(secondToSave));

        List<OrderHistoryDto> actual = orderHistoryService.findAll().stream()
                .map(CustomModelMapper::mapHistoryToDto)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(firstSaved, secondSaved), actual);
    }

    @Test
    void findOne() {
        Worker savedWorker = getSavedWorker();
        OrderHistoryDto expected = OrderHistoryDto
                .builder()
                .comment("Comment")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_THE_WAY_TO_ACCEPTED)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistory savedHistory = orderHistoryService.save(expected);
        OrderHistoryDto actual = CustomModelMapper.mapHistoryToDto(orderHistoryService.findOne(savedHistory.getId()));
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        Worker savedWorker = getSavedWorker();
        OrderHistoryDto toUpdate = OrderHistoryDto
                .builder()
                .comment("Comment")
                .changedTypeEnum(OrderStateChangeType.FROM_ON_THE_WAY_TO_ACCEPTED)
                .sentAt(LocalDateTime.now())
                .changedAt(LocalDateTime.now())
                .worker(CustomModelMapper.mapWorkerToDto(savedWorker))
                .build();
        OrderHistoryDto expected = CustomModelMapper.mapHistoryToDto(orderHistoryService.save(toUpdate));
        expected.setChangedAt(LocalDateTime.now());
        OrderHistoryDto actual = CustomModelMapper.mapHistoryToDto(orderHistoryService.update(expected));
        assertEquals(expected, actual);
    }

    private Worker getSavedWorker() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointDtoToTest = new OrderProcessingPointDto();
        orderProcessingPointDtoToTest.setLocation("Minsk-Belarus");
        orderProcessingPointDtoToTest.setWorkingPlaceType(PROCESSING_POINT);
        orderProcessingPointDtoToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPointDtoToTest);
        WorkerDto expectedWorker = WorkerDto
                .builder()
                .surname("Vlad")
                .password("awqwrr")
                .role(Role.ROLE_ADMIN)
                .name("Vlad")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();
        return workerService.save(expectedWorker);
    }
}