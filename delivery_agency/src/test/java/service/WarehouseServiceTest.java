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
        WarehouseDto warehouse = new WarehouseDto();
        warehouse.setId(1L);
        String expected = "Minsk";
        warehouse.setLocation(expected);
        warehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseService.save(warehouse);

        String actual = warehouseService.findOne(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() throws SQLException {
        WarehouseDto firstWarehouse = new WarehouseDto();
        firstWarehouse.setId(1L);
        firstWarehouse.setLocation("");
        firstWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setId(2L);
        secondWarehouse.setLocation("");
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setId(3L);
        thirdWarehouse.setLocation("");
        thirdWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(firstWarehouse);
        warehouseService.save(secondWarehouse);
        warehouseService.save(thirdWarehouse);

        warehouseService.delete(secondWarehouse.getId());

        int expected = 2;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWarehouses() throws SQLException {
        WarehouseDto firstWarehouse = new WarehouseDto();
        firstWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        firstWarehouse.setLocation("Moscow");
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
        warehouseService.save(firstWarehouse);
        warehouseService.save(secondWarehouse);
        warehouseService.save(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWarehouse() throws SQLException {
        WarehouseDto warehouse = new WarehouseDto();
        warehouse.setId(1L);
        String expected = "Vitebsk";
        warehouse.setLocation(expected);
        warehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(warehouse);

        String actual = warehouseService.findOne(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToAdd = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();
        OrderDto secondOrderToAdd =OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
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
        expectedDto.setExpectedOrders(Arrays.asList(firstOrderToAdd, secondOrderToAdd));
        expectedDto.setDispatchedOrders(Arrays.asList(firstOrderToAdd, secondOrderToAdd));
        expectedDto.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseService.save(expectedDto);

        String newLocation = "Minsk";

        expectedDto.setLocation(newLocation);

        expectedDto.setDispatchedOrders(Collections.singletonList(secondOrderToAdd));
        expectedDto.setExpectedOrders(Collections.singletonList(secondOrderToAdd));

        Warehouse actual = warehouseService.update(expectedDto);

        WarehouseDto actualDto = modelMapper.map(actual, WarehouseDto.class);

        Assertions.assertEquals(expectedDto, actualDto);
    }
}
