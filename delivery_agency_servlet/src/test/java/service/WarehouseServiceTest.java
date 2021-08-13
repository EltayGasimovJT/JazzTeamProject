package service;

import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.WarehouseServiceImpl;

import java.sql.SQLException;

class WarehouseServiceTest {
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Test
    void addWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        String expected = "Minsk";
        warehouse.setLocation(expected);
        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() throws SQLException {
        Warehouse firstWarehouse = new Warehouse();
        firstWarehouse.setId(1L);
        Warehouse secondWarehouse = new Warehouse();
        secondWarehouse.setId(2L);
        Warehouse thirdWarehouse = new Warehouse();
        thirdWarehouse.setId(3L);

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        warehouseService.deleteWarehouse(secondWarehouse);

        int expected = 2;

        int actual = warehouseService.findAllWarehouses().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWarehouses() throws SQLException {
        Warehouse firstWarehouse = new Warehouse();
        Warehouse secondWarehouse = new Warehouse();
        Warehouse thirdWarehouse = new Warehouse();

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAllWarehouses().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        String expected = "Vitebsk";
        warehouse.setLocation(expected);

        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLocation("Vitebsk");

        warehouseService.addWarehouse(warehouse);

        String expectedLocation = "Minsk";

        warehouse.setLocation(expectedLocation);

        String actualLocation = warehouseService.update(warehouse).getLocation();

        Assertions.assertEquals(expectedLocation, actualLocation);
    }
}