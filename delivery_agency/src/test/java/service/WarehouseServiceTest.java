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
        WarehouseDto warehouse = new WarehouseDto();
        warehouse.setId(1L);
        String expected = "Minsk";
        warehouse.setLocation(expected);
        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() throws SQLException {
        WarehouseDto firstWarehouse = new WarehouseDto();
        firstWarehouse.setId(1L);
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setId(2L);
        WarehouseDto thirdWarehouse = new WarehouseDto();
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
        WarehouseDto firstWarehouse = new WarehouseDto();
        WarehouseDto secondWarehouse = new WarehouseDto();
        WarehouseDto thirdWarehouse = new WarehouseDto();

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAllWarehouses().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWarehouse() throws SQLException {
        WarehouseDto warehouse = new WarehouseDto();
        warehouse.setId(1L);
        String expected = "Vitebsk";
        warehouse.setLocation(expected);

        warehouseService.addWarehouse(warehouse);

        String actual = warehouseService.getWarehouse(warehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        WarehouseDto expected = new WarehouseDto();
        expected.setId(1L);
        expected.setLocation("Vitebsk");

        warehouseService.addWarehouse(expected);

        String newLocation = "Minsk";

        expected.setLocation(newLocation);

        WarehouseDto actual = warehouseService.update(expected);

        Assertions.assertEquals(expected, actual);
    }
}