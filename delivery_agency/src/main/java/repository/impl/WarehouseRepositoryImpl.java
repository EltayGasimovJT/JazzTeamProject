package repository.impl;

import entity.Warehouse;
import repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;

public class WarehouseRepositoryImpl implements WarehouseRepository {
    private final List<Warehouse> warehouses = new ArrayList<>();

    @Override
    public Warehouse save(Warehouse warehouseToSave) {
        warehouses.add(warehouseToSave);
        return warehouseToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        warehouses.remove(findOne(idForDelete));
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouses;
    }

    @Override
    public Warehouse findOne(Long idForSearch) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Warehouse update(Warehouse newWarehouse)  {
        Warehouse warehouseToUpdate = findOne(newWarehouse.getId());
        warehouses.remove(warehouseToUpdate);
        warehouseToUpdate.setLocation(newWarehouse.getLocation());
        warehouseToUpdate.setConnectedWarehouses(newWarehouse.getConnectedWarehouses());
        warehouseToUpdate.setOrderProcessingPoints(newWarehouse.getOrderProcessingPoints());
        warehouseToUpdate.setDispatchedOrders(newWarehouse.getDispatchedOrders());
        warehouseToUpdate.setExpectedOrders(newWarehouse.getExpectedOrders());
        warehouses.add(warehouseToUpdate);
        return warehouseToUpdate;
    }
}