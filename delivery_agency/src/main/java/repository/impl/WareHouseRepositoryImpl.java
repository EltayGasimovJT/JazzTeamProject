package repository.impl;

import entity.Warehouse;
import repository.WareHouseRepository;

import java.util.ArrayList;
import java.util.List;

public class WareHouseRepositoryImpl implements WareHouseRepository {
    private final List<Warehouse> warehouses = new ArrayList<>();

    @Override
    public Warehouse save(Warehouse warehouse) {
        warehouses.add(warehouse);
        return warehouse;
    }

    @Override
    public void delete(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouses;
    }

    @Override
    public Warehouse findOne(long id) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Warehouse update(Warehouse update) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getId().equals(update.getId())) {
                warehouse.setLocation(update.getLocation());
                warehouse.setConnectedWarehouses(update.getConnectedWarehouses());
                warehouse.setOrderProcessingPoints(update.getOrderProcessingPoints());
                warehouse.setDispatchedOrders(update.getDispatchedOrders());
                warehouse.setExpectedOrders(update.getExpectedOrders());
            }
        }
        return update;
    }
}
