package service.impl;

import dto.WarehouseDto;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import mapping.OrderMapper;
import org.modelmapper.ModelMapper;
import repository.WarehouseRepository;
import repository.impl.WarehouseRepositoryImpl;
import service.WarehouseService;
import validator.WarehouseValidator;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository = new WarehouseRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Warehouse save(WarehouseDto warehouseDtoToSave) {
        Warehouse warehouseToSave = new Warehouse();

        warehouseToSave.setId(warehouseDtoToSave.getId());
        warehouseToSave.setLocation(warehouseDtoToSave.getLocation());
        warehouseToSave.setOrderProcessingPoints(warehouseDtoToSave.getOrderProcessingPoints()
                .stream()
                .map(orderProcessingPointDto -> modelMapper.map(orderProcessingPointDto,OrderProcessingPoint.class))
                .collect(Collectors.toList()));

        WarehouseValidator.validateOnSave(warehouseToSave);
        return warehouseRepository.save(warehouseToSave);
    }

    @Override
    public void delete(Long idForDelete) {
        WarehouseValidator.validateWarehouse(warehouseRepository.findOne(idForDelete));
        warehouseRepository.delete(idForDelete);
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehousesFromRepository = warehouseRepository.findAll();
        WarehouseValidator.validateWarehouseList(warehousesFromRepository);

        return warehousesFromRepository;
    }

    @Override
    public Warehouse findOne(long idForSearch) {
        Warehouse foundWarehouse = warehouseRepository.findOne(idForSearch);
        WarehouseValidator.validateWarehouse(foundWarehouse);

        return foundWarehouse;
    }

    @Override
    public Warehouse update(WarehouseDto warehouseDtoToUpdate) {
        Warehouse warehouseToUpdate = warehouseRepository.findOne(warehouseDtoToUpdate.getId());

        WarehouseValidator.validateWarehouse(warehouseToUpdate);

        warehouseToUpdate.setExpectedOrders(warehouseDtoToUpdate.getExpectedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        warehouseToUpdate.setDispatchedOrders(warehouseDtoToUpdate.getDispatchedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        warehouseToUpdate.setOrderProcessingPoints(warehouseDtoToUpdate.getOrderProcessingPoints().stream().
                map(orderProcessingPointDto -> modelMapper.map(orderProcessingPointDto, OrderProcessingPoint.class))
                .collect(Collectors.toList()));

        return warehouseRepository.update(warehouseToUpdate);
    }
}