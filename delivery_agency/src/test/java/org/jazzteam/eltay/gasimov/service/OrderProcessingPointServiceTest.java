package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
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
                Arguments.of(firstProcessingPointToTest),
                Arguments.of(secondProcessingPointToTest),
                Arguments.of(thirdProcessingPointToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("testOrderProcessingPoints")
    void addOrderProcessingPoint(OrderProcessingPointDto expected) {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        expected.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(expected);
        expected.setId(savedProcessingPoint.getId());
        OrderProcessingPointDto actual = modelMapper.map(orderProcessingPointService.findOne(savedProcessingPoint.getId()), OrderProcessingPointDto.class);
        Assertions.assertEquals(expected, actual);
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
        firstProcessingPointToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        OrderProcessingPointDto secondProcessingPointToSave = new OrderProcessingPointDto();
        secondProcessingPointToSave.setLocation("Grodno-Belarus");
        secondProcessingPointToSave.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        secondProcessingPointToSave.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(firstProcessingPointToTest);
        orderProcessingPointService.save(secondProcessingPointToSave);

        orderProcessingPointService.delete(savedProcessingPoint.getId());

        List<OrderProcessingPoint> actualProcessingPoints = orderProcessingPointService.findAll();

        Assertions.assertFalse(actualProcessingPoints.isEmpty());
    }

    @Test
    void findAllOrderProcessingPoints() {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto firstProcessingPointTest = new OrderProcessingPointDto();
        firstProcessingPointTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        firstProcessingPointTest.setLocation("Minsk-Belarus");
        firstProcessingPointTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        OrderProcessingPointDto secondProcessingPointTest = new OrderProcessingPointDto();
        secondProcessingPointTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        secondProcessingPointTest.setLocation("Gomel-Belarus");
        secondProcessingPointTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPointDto thirdProcessingPointTest = new OrderProcessingPointDto();
        thirdProcessingPointTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
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
        firstProcessingPointToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));

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
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);

        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setLocation("Minsk-Belarus");
        expectedProcessingPointDto.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        expectedProcessingPointDto.setExpectedOrders(new ArrayList<>());
        expectedProcessingPointDto.setDispatchedOrders(new ArrayList<>());
        expectedProcessingPointDto.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(expectedProcessingPointDto);

        String newLocation = "Homel-Belarus";

        expectedProcessingPointDto.setLocation(newLocation);

        OrderProcessingPoint actual = orderProcessingPointService.update(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class));

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouse(CustomModelMapper.mapWarehouseToDto(actual.getWarehouse()));

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }
}