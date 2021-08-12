package service.impl;

import dto.WarehouseDto;
import entity.Warehouse;
import repository.WareHouseRepository;
import repository.impl.WarehouseRepositoryImpl;
import service.WarehouseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {
    private final WareHouseRepository wareHouseRepository = new WarehouseRepositoryImpl();

    @Override
    public WarehouseDto addWarehouse(WarehouseDto warehouse) {
        Warehouse save = wareHouseRepository.save(fromDtoToWarehouse(warehouse));
        return fromWarehouseToDTO(save);
    }

    @Override
    public void deleteWarehouse(Long id) {
        wareHouseRepository.delete(id);
    }

    @Override
    public List<WarehouseDto> findAllWarehouses() {
        List<Warehouse> warehouses = wareHouseRepository.findAll();
        List<WarehouseDto> warehouseDtos = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {
            warehouseDtos.add(fromWarehouseToDTO(warehouse));
        }
        return warehouseDtos;
    }

    @Override
    public WarehouseDto getWarehouse(long id) {
        final Warehouse warehouse = wareHouseRepository.findOne(id);
        return fromWarehouseToDTO(warehouse);
    }

    @Override
    public WarehouseDto update(WarehouseDto warehouse) throws SQLException {
        final Warehouse update = wareHouseRepository.update(fromDtoToWarehouse(warehouse));
        return fromWarehouseToDTO(update);
    }

    private WarehouseDto fromWarehouseToDTO(Warehouse warehouse) {
        return WarehouseDto.builder()
                .id(warehouse.getId())
                .dispatchedOrders(warehouse.getDispatchedOrders())
                .expectedOrders(warehouse.getExpectedOrders())
                .location(warehouse.getLocation())
                .connectedWarehouses(warehouse.getConnectedWarehouses())
                .orderProcessingPoints(warehouse.getOrderProcessingPoints())
                .build();
    }

    private Warehouse fromDtoToWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDto.getId());
        warehouse.setConnectedWarehouses(warehouseDto.getConnectedWarehouses());
        warehouse.setExpectedOrders(warehouseDto.getExpectedOrders());
        warehouse.setDispatchedOrders(warehouseDto.getDispatchedOrders());
        warehouse.setLocation(warehouseDto.getLocation());
        warehouse.setOrderProcessingPoints(warehouseDto.getOrderProcessingPoints());
        return warehouse;
    }
}
