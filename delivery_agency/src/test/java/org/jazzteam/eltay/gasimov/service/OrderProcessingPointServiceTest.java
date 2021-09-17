package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/drop-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Rollback
class OrderProcessingPointServiceTest {
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WarehouseService warehouseService;

    private static Stream<Arguments> testOrderProcessingPoints() {
        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Minsk-Belarus");
        firstProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPointDto secondProcessingPointToTest = new OrderProcessingPointDto();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Moscow-Belarus");
        secondProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPointDto thirdProcessingPointToTest = new OrderProcessingPointDto();
        thirdProcessingPointToTest.setId(3L);
        thirdProcessingPointToTest.setLocation("Grodno-Belarus");
        thirdProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        return Stream.of(
                Arguments.of(firstProcessingPointToTest, "Minsk-Belarus"),
                Arguments.of(secondProcessingPointToTest, "Moscow-Belarus"),
                Arguments.of(thirdProcessingPointToTest, "Grodno-Belarus")
        );
    }

    @ParameterizedTest
    @MethodSource("testOrderProcessingPoints")
    void addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPoint, String expectedLocation) {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        orderProcessingPoint.setWarehouseId(savedWarehouse.getId());
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.findOne(savedProcessingPoint.getId()).getLocation();
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setLocation("Polotsk-Belarus");
        firstProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        firstProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        firstProcessingPointToTest.setWarehouseId(savedWarehouse.getId());

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(firstProcessingPointToTest);

        orderProcessingPointService.delete(savedProcessingPoint.getId());

        List<OrderProcessingPoint> actualProcessingPoints = orderProcessingPointService.findAll();

        Assertions.assertFalse(actualProcessingPoints.isEmpty());
    }

    @Test
    void findAllOrderProcessingPoints() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto firstProcessingPointTest = new OrderProcessingPointDto();
        firstProcessingPointTest.setWarehouseId(savedWarehouse.getId());
        firstProcessingPointTest.setLocation("Minsk-Belarus");
        firstProcessingPointTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        OrderProcessingPointDto secondProcessingPointTest = new OrderProcessingPointDto();
        secondProcessingPointTest.setWarehouseId(savedWarehouse.getId());
        secondProcessingPointTest.setLocation("Gomel-Belarus");
        secondProcessingPointTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPointDto thirdProcessingPointTest = new OrderProcessingPointDto();
        thirdProcessingPointTest.setWarehouseId(savedWarehouse.getId());
        thirdProcessingPointTest.setLocation("Polotsk-Belarus");
        thirdProcessingPointTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        orderProcessingPointService.save(firstProcessingPointTest);
        orderProcessingPointService.save(secondProcessingPointTest);
        orderProcessingPointService.save(thirdProcessingPointTest);

        List<OrderProcessingPoint> actualOrderProcessingPoints = orderProcessingPointService.findAll();

        int expectedSize = 3;
        Assertions.assertEquals(expectedSize, actualOrderProcessingPoints.size());
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setLocation("Polotsk-Belarus");
        firstProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        firstProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        firstProcessingPointToTest.setWarehouseId(savedWarehouse.getId());

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(firstProcessingPointToTest);

        warehouseToSave.setId(savedProcessingPoint.getId());
        OrderProcessingPoint actual = orderProcessingPointService.findOne(savedProcessingPoint.getId());

        OrderProcessingPoint expected = modelMapper.map(firstProcessingPointToTest, OrderProcessingPoint.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setLocation("Minsk-Belarus");
        expectedProcessingPointDto.setWarehouseId(savedWarehouse.getId());
        expectedProcessingPointDto.setExpectedOrders(new ArrayList<>());
        expectedProcessingPointDto.setDispatchedOrders(new ArrayList<>());
        expectedProcessingPointDto.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(expectedProcessingPointDto);

        String newLocation = "Homel-Belarus";

        expectedProcessingPointDto.setLocation(newLocation);

        OrderProcessingPoint actual = orderProcessingPointService.update(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class));

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouseId(actual.getWarehouse().getId());

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }
}