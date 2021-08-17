package service;

import dto.*;
import entity.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import service.impl.WarehouseServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class WarehouseServiceTest {
    private final WarehouseService warehouseService = new WarehouseServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void addWarehouse() throws SQLException {
        WarehouseDto warehouseToTest = new WarehouseDto();
        warehouseToTest.setId(1L);
        String expected = "Minsk";
        warehouseToTest.setLocation(expected);
        warehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseService.save(warehouseToTest);

        String actual = warehouseService.findOne(warehouseToTest.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() throws SQLException {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setId(1L);
        firstWarehouseToTest.setLocation("");
        firstWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto secondWarehouseToTest = new WarehouseDto();
        secondWarehouseToTest.setId(2L);
        secondWarehouseToTest.setLocation("");
        secondWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto thirdWarehouseToTest = new WarehouseDto();
        thirdWarehouseToTest.setId(3L);
        thirdWarehouseToTest.setLocation("");
        thirdWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(firstWarehouseToTest);
        warehouseService.save(secondWarehouseToTest);
        warehouseService.save(thirdWarehouseToTest);

        warehouseService.delete(secondWarehouseToTest.getId());

        int expected = 2;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWarehouses() throws SQLException {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        firstWarehouseToTest.setLocation("Moscow");
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        secondWarehouse.setLocation("Moscow");
        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        thirdWarehouse.setLocation("Moscow");
        warehouseService.save(firstWarehouseToTest);
        warehouseService.save(secondWarehouse);
        warehouseService.save(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWarehouse() throws SQLException {
        WarehouseDto warehouseToTest = new WarehouseDto();
        warehouseToTest.setId(1L);
        String expected = "Vitebsk";
        warehouseToTest.setLocation(expected);
        warehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(warehouseToTest);

        String actual = warehouseService.findOne(warehouseToTest.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();
        OrderDto secondOrderToTest = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();

        WarehouseDto expectedDto = new WarehouseDto();
        expectedDto.setId(1L);
        expectedDto.setLocation("Vitebsk");
        expectedDto.setExpectedOrders(Arrays.asList(firstOrderToTest, secondOrderToTest));
        expectedDto.setDispatchedOrders(Arrays.asList(firstOrderToTest, secondOrderToTest));
        expectedDto.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseService.save(expectedDto);

        String newLocation = "Minsk";

        expectedDto.setLocation(newLocation);

        expectedDto.setDispatchedOrders(Collections.singletonList(secondOrderToTest));
        expectedDto.setExpectedOrders(Collections.singletonList(secondOrderToTest));

        Warehouse actual = warehouseService.update(expectedDto);

        WarehouseDto actualDto = modelMapper.map(actual, WarehouseDto.class);

        Assertions.assertEquals(expectedDto, actualDto);
    }
}
