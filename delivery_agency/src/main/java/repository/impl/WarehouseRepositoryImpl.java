package repository.impl;

import entity.Warehouse;
import repository.WareHouseRepository;

import java.util.ArrayList;
import java.util.List;

public class WarehouseRepositoryImpl implements WareHouseRepository {
    private final List<Warehouse> warehouses = new ArrayList<>();

    @Override
    public Warehouse save(Warehouse warehouse) {
        warehouses.add(warehouse);
        return warehouse;
    }

    @Override
    public void delete(Long id) {
        warehouses.remove(findOne(id));
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouses;
    }

    @Override
    public Warehouse findOne(Long id) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Warehouse update(Warehouse update)  {
        Warehouse warehouse = findOne(update.getId());
        warehouses.remove(warehouse);
        warehouse.setLocation(update.getLocation());
        warehouse.setConnectedWarehouses(update.getConnectedWarehouses());
        warehouse.setOrderProcessingPoints(update.getOrderProcessingPoints());
        warehouse.setDispatchedOrders(update.getDispatchedOrders());
        warehouse.setExpectedOrders(update.getExpectedOrders());
        warehouses.add(warehouse);
        return warehouse;
    }
}