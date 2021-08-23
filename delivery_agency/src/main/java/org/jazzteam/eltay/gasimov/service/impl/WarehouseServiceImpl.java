package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.WarehouseRepository;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.jazzteam.eltay.gasimov.validator.WarehouseValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value =  "warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Warehouse save(WarehouseDto warehouseDtoToSave) throws IllegalArgumentException {
        Warehouse warehouseToSave = CustomModelMapper.mapDtoToWarehouse(warehouseDtoToSave);

        WarehouseValidator.validateOnSave(warehouseToSave);
        return warehouseRepository.save(warehouseToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Warehouse> foundClientFromRepository = warehouseRepository.findById(idForDelete);
        Warehouse foundWarehouse = foundClientFromRepository.orElseGet(Warehouse::new);
        WarehouseValidator.validateWarehouse(foundWarehouse);
        warehouseRepository.deleteById(idForDelete);
    }

    @Override
    public List<Warehouse> findAll() throws IllegalArgumentException {
        List<Warehouse> warehousesFromRepository = warehouseRepository.findAll();
        WarehouseValidator.validateWarehouseList(warehousesFromRepository);

        return warehousesFromRepository;
    }

    @Override
    public Warehouse findOne(long idForSearch) throws IllegalArgumentException {
        Optional<Warehouse> foundClientFromRepository = warehouseRepository.findById(idForSearch);
        Warehouse foundWarehouse = foundClientFromRepository.orElseGet(Warehouse::new);
        WarehouseValidator.validateWarehouse(foundWarehouse);

        return foundWarehouse;
    }

    @Override
    public Warehouse findByLocation(String locationForSearch) throws SQLException {
        Warehouse foundWarehouseFromRepository = warehouseRepository.findByLocation(locationForSearch);
        WarehouseValidator.validateWarehouse(foundWarehouseFromRepository);
        return foundWarehouseFromRepository;
    }

    @Override
    public Warehouse update(WarehouseDto warehouseDtoToUpdate) throws IllegalArgumentException {
        Optional<Warehouse> foundClientFromRepository = warehouseRepository.findById(warehouseDtoToUpdate.getId());
        Warehouse warehouseToUpdate = foundClientFromRepository.orElseGet(Warehouse::new);

        WarehouseValidator.validateWarehouse(warehouseToUpdate);

        warehouseToUpdate.setExpectedOrders(warehouseDtoToUpdate.getExpectedOrders().stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList()));
        warehouseToUpdate.setDispatchedOrders(warehouseDtoToUpdate.getDispatchedOrders().stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList()));
        warehouseToUpdate.setOrderProcessingPoints(warehouseDtoToUpdate.getOrderProcessingPoints().stream().
                map(orderProcessingPointDto -> modelMapper.map(orderProcessingPointDto, OrderProcessingPoint.class))
                .collect(Collectors.toList()));

        return warehouseRepository.save(warehouseToUpdate);
    }
}