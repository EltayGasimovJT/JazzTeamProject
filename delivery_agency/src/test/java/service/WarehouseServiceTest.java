package service;

import dto.WarehouseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.WarehouseServiceImpl;

import java.sql.SQLException;

class WarehouseServiceTest {
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Test
    void addWarehouse() throws SQLException {
        WarehouseDto warehouse = WarehouseDto.builder().build();
        warehouse.setId(1L);
        String expected = "Minsk";
        warehouse.setLocation(expected);
        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() throws SQLException {
        WarehouseDto firstWarehouse = WarehouseDto.builder().build();
        firstWarehouse.setId(1L);
        WarehouseDto secondWarehouse = WarehouseDto.builder().build();
        secondWarehouse.setId(2L);
        WarehouseDto thirdWarehouse = WarehouseDto.builder().build();
        thirdWarehouse.setId(3L);

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        warehouseService.deleteWarehouse(secondWarehouse.getId());

        int expected = 2;

        int actual = warehouseService.findAllWarehouses().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWarehouses() throws SQLException {
        WarehouseDto firstWarehouse = WarehouseDto.builder().build();
        WarehouseDto secondWarehouse = WarehouseDto.builder().build();
        WarehouseDto thirdWarehouse = WarehouseDto.builder().build();

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAllWarehouses().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWarehouse() throws SQLException {
        WarehouseDto warehouse = WarehouseDto.builder().build();
        warehouse.setId(1L);
        String expected = "Vitebsk";
        warehouse.setLocation(expected);

        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        WarehouseDto expected = WarehouseDto.builder().build();
        expected.setId(1L);
        expected.setLocation("Vitebsk");

        warehouseService.addWarehouse(expected);

        String newLocation = "Minsk";

        expected.setLocation(newLocation);

        WarehouseDto actual = warehouseService.update(expected);

        Assertions.assertEquals(expected, actual);
    }
}