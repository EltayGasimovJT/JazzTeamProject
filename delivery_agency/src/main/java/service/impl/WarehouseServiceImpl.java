package service.impl;

import dto.OrderDto;
import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import entity.Order;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.modelmapper.ModelMapper;
import repository.WarehouseRepository;
import repository.impl.WarehouseRepositoryImpl;
import service.WarehouseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository = new WarehouseRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Warehouse save(WarehouseDto warehouseDto) {
        Warehouse warehouseToSave = new Warehouse();
        List<OrderProcessingPoint> processingPointsToSave = new ArrayList<>();

        for (OrderProcessingPointDto orderProcessingPoint : warehouseDto.getOrderProcessingPoints()) {
            OrderProcessingPoint orderProcessingPointToSave = new OrderProcessingPoint();
            orderProcessingPointToSave.setId(orderProcessingPoint.getId());
            orderProcessingPointToSave.setLocation(orderProcessingPoint.getLocation());
        }

        warehouseToSave.setId(warehouseDto.getId());
        warehouseToSave.setLocation(warehouseDto.getLocation());
        warehouseToSave.setOrderProcessingPoints(processingPointsToSave);

        return warehouseRepository.save(warehouseToSave);
    }

    @Override
    public void delete(Long id) {
        warehouseRepository.delete(id);
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehousesFromRepository = warehouseRepository.findAll();
        if (warehousesFromRepository.isEmpty()) {
            throw new IllegalArgumentException("There is no warehouses on database!!!");
        }

        return warehousesFromRepository;
    }

    @Override
    public Warehouse findOne(long id) {
        Warehouse warehouseById = warehouseRepository.findOne(id);
        if (warehouseById == null) {
            throw new IllegalArgumentException("There is no warehouse with this Id!!!");
        }

        return warehouseById;
    }

    @Override
    public Warehouse update(WarehouseDto warehouse) {
        List<Order> expectedOrdersToUpdate = new ArrayList<>();
        List<Order> dispatchedOrdersToUpdate = new ArrayList<>();

        for (OrderDto dispatchedOrder : warehouse.getDispatchedOrders()) {
            Order orderToDispatch = Order.builder()
                    .id(dispatchedOrder.getId())
                    .build();

            dispatchedOrdersToUpdate.add(orderToDispatch);
        }

        for (OrderDto expectedOrder : warehouse.getExpectedOrders()) {
            Order expectedOrderToSave = Order.builder()
                    .id(expectedOrder.getId())
                    .build();
            expectedOrdersToUpdate.add(expectedOrderToSave);
        }

        Warehouse warehouseToUpdate = warehouseRepository.findOne(warehouse.getId());

        if (warehouseToUpdate == null) {
            throw new IllegalArgumentException("There is no warehouses to update!!!");
        }

        warehouseToUpdate.setExpectedOrders(expectedOrdersToUpdate);
        warehouseToUpdate.setDispatchedOrders(dispatchedOrdersToUpdate);
        warehouseToUpdate.setOrderProcessingPoints(warehouse.getOrderProcessingPoints().stream().
                map(orderProcessingPointDto -> modelMapper.map(orderProcessingPointDto, OrderProcessingPoint.class))
                .collect(Collectors.toList()));

        return warehouseRepository.update(warehouseToUpdate);
    }
}