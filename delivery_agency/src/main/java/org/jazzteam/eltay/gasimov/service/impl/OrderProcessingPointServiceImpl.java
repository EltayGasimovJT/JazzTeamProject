package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.OrderProcessingPointRepository;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.jazzteam.eltay.gasimov.validator.OrderProcessingPointValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "orderProcessingPointService")
public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    @Autowired
    private OrderProcessingPointRepository orderProcessingPointRepository;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave) throws IllegalArgumentException {
        OrderProcessingPoint orderProcessingPointToSave = new OrderProcessingPoint();
        orderProcessingPointToSave.setId(processingPointDtoToSave.getId());
        orderProcessingPointToSave.setLocation(processingPointDtoToSave.getLocation());
        orderProcessingPointToSave.setWorkingPlaceType(processingPointDtoToSave.getWorkingPlaceType().toString());
        String location = processingPointDtoToSave.getLocation();
        String[] splitLocation = location.split("-");
        Warehouse warehouseToSave = warehouseService.findByLocation(splitLocation[1]);
        orderProcessingPointToSave.setWarehouse(warehouseToSave);
        OrderProcessingPointValidator.validateOnSave(orderProcessingPointToSave);

        return orderProcessingPointRepository.save(orderProcessingPointToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<OrderProcessingPoint> foundClientFromRepository = orderProcessingPointRepository.findById(idForDelete);
        OrderProcessingPoint foundProcessingPoint = foundClientFromRepository.orElseGet(OrderProcessingPoint::new);
        OrderProcessingPointValidator.validateProcessingPoint(foundProcessingPoint);
        orderProcessingPointRepository.deleteById(idForDelete);
    }

    @Override
    public List<OrderProcessingPoint> findAll() throws IllegalArgumentException {
        return orderProcessingPointRepository.findAll();
    }

    @Override
    public OrderProcessingPoint findOne(long idForSearch) throws IllegalArgumentException {
        Optional<OrderProcessingPoint> foundClientFromRepository = orderProcessingPointRepository.findById(idForSearch);
        OrderProcessingPoint foundProcessingPoint = foundClientFromRepository.orElseGet(OrderProcessingPoint::new);
        OrderProcessingPointValidator.validateProcessingPoint(foundProcessingPoint);
        return foundProcessingPoint;
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate) throws IllegalArgumentException {
        Optional<OrderProcessingPoint> foundClientFromRepository = orderProcessingPointRepository.findById(processingPointDtoToUpdate.getId());
        OrderProcessingPoint foundProcessingPoint = foundClientFromRepository.orElseGet(OrderProcessingPoint::new);
        OrderProcessingPointValidator.validateProcessingPoint(foundProcessingPoint);
        foundProcessingPoint.setId(processingPointDtoToUpdate.getId());
        foundProcessingPoint.setLocation(processingPointDtoToUpdate.getLocation());
        foundProcessingPoint.setWarehouse(modelMapper.map(warehouseService.findOne(processingPointDtoToUpdate.getWarehouseId()), Warehouse.class));
        if (processingPointDtoToUpdate.getExpectedOrders() != null) {
            foundProcessingPoint.setExpectedOrders(processingPointDtoToUpdate.getExpectedOrders().stream()
                    .map(CustomModelMapper::mapDtoToOrder)
                    .collect(Collectors.toList()));
        }
        if (processingPointDtoToUpdate.getDispatchedOrders() != null) {
            foundProcessingPoint.setDispatchedOrders(processingPointDtoToUpdate.getDispatchedOrders().stream()
                    .map(CustomModelMapper::mapDtoToOrder)
                    .collect(Collectors.toList()));
        }
        return orderProcessingPointRepository.save(foundProcessingPoint);
    }

    @Override
    public OrderProcessingPoint findByLocation(String locationForFind) {
        OrderProcessingPoint foundFromRepository = orderProcessingPointRepository.findByLocation(locationForFind);
        OrderProcessingPointValidator.validateProcessingPoint(foundFromRepository);
        return foundFromRepository;
    }

}