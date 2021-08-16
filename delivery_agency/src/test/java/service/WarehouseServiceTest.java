package service;

import dto.OrderDto;
import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import entity.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import service.impl.WarehouseServiceImpl;

import java.sql.SQLException;
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
        firstWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setId(2L);
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setId(3L);
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
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
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
        WarehouseDto expectedDto = new WarehouseDto();
        expectedDto.setId(1L);
        expectedDto.setLocation("Vitebsk");
        expectedDto.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseService.save(expectedDto);

        String newLocation = "Minsk";

        expectedDto.setLocation(newLocation);

        expectedDto.setDispatchedOrders(Arrays.asList(
                new OrderDto(),
                new OrderDto()
        ));
        expectedDto.setExpectedOrders(
                Arrays.asList(
                        new OrderDto(),
                        new OrderDto()
        ));

        Warehouse actual = warehouseService.update(expectedDto);

        WarehouseDto actualDto = modelMapper.map(actual, WarehouseDto.class);

        Assertions.assertEquals(expectedDto, actualDto);
    }
}
