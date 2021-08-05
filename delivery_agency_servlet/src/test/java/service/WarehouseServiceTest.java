package service;

import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.WarehouseServiceImpl;

import java.sql.SQLException;

class WarehouseServiceTest {
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Test
    void addWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLocation("Minsk");
        warehouseService.addWarehouse(warehouse);

        Warehouse warehouse1 = warehouseService.getWarehouse(warehouse.getId());

        Assert.assertEquals("Minsk", warehouse1.getLocation());
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

        Assert.assertEquals(2, warehouseService.findAllWarehouses().size());
    }

    @Test
    void findAllWarehouses() throws SQLException {
        Warehouse firstWarehouse = new Warehouse();
        Warehouse secondWarehouse = new Warehouse();
        Warehouse thirdWarehouse = new Warehouse();

        warehouseService.addWarehouse(firstWarehouse);
        warehouseService.addWarehouse(secondWarehouse);
        warehouseService.addWarehouse(thirdWarehouse);

        Assert.assertEquals(3, warehouseService.findAllWarehouses().size());
    }

    @Test
    void getWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLocation("Vitebsk");

        warehouseService.addWarehouse(warehouse);

        Warehouse getWarehouseById = warehouseService.getWarehouse(warehouse.getId());

        Assert.assertEquals("Vitebsk", getWarehouseById.getLocation());
    }

    @Test
    void update() throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLocation("Vitebsk");

        warehouseService.addWarehouse(warehouse);

        warehouse.setLocation("Minsk");

        Warehouse update = warehouseService.update(warehouse);

        Assert.assertEquals("Minsk", update.getLocation());
    }
}