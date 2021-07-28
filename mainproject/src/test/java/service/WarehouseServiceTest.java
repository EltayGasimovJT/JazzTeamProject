package service;

import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.WarehouseServiceImpl;

class WarehouseServiceTest {
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Test
    void addWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setLocation("Minsk");
        warehouseService.addWarehouse(warehouse);

        Warehouse warehouse1 = warehouseService.getWarehouse(warehouse.getId());

        Assert.assertEquals("Minsk", warehouse1.getLocation());
    }

    @Test
    void deleteWarehouse() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(2);
        Warehouse warehouse3 = new Warehouse();
        warehouse3.setId(3);

        warehouseService.addWarehouse(warehouse1);
        warehouseService.addWarehouse(warehouse2);
        warehouseService.addWarehouse(warehouse3);

        warehouseService.deleteWarehouse(warehouse2);

        Assert.assertEquals(2, warehouseService.findAllWarehouses().size());
    }

    @Test
    void findAllWarehouses() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(2);
        Warehouse warehouse3 = new Warehouse();
        warehouse3.setId(3);

        warehouseService.addWarehouse(warehouse1);
        warehouseService.addWarehouse(warehouse2);
        warehouseService.addWarehouse(warehouse3);

        Assert.assertEquals(3, warehouseService.findAllWarehouses().size());
    }

    @Test
    void getWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setLocation("Vitebsk");

        warehouseService.addWarehouse(warehouse);

        Warehouse warehouse1 = warehouseService.getWarehouse(warehouse.getId());

        Assert.assertEquals("Vitebsk", warehouse1.getLocation());
    }

    @Test
    void update() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setLocation("Vitebsk");

        warehouseService.addWarehouse(warehouse);

        warehouse.setLocation("Minsk");

        Warehouse update = warehouseService.update(warehouse);

        Assert.assertEquals("Minsk", update.getLocation());
    }
}