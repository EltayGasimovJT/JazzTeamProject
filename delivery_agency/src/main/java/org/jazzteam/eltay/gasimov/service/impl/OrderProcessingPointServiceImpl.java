package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jazzteam.eltay.gasimov.repository.OrderProcessingPointRepository;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.jazzteam.eltay.gasimov.validator.OrderProcessingPointValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "orderProcessingPointService")
public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    @Autowired
    private OrderProcessingPointRepository orderProcessingPointRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave) throws IllegalArgumentException {
        OrderProcessingPoint orderProcessingPointToSave = new OrderProcessingPoint();
        orderProcessingPointToSave.setId(processingPointDtoToSave.getId());
        orderProcessingPointToSave.setLocation(processingPointDtoToSave.getLocation());
        Warehouse warehouseToSave = new Warehouse();
        warehouseToSave.setId(processingPointDtoToSave.getWarehouse().getId());
        warehouseToSave.setLocation(processingPointDtoToSave.getWarehouse().getLocation());
        orderProcessingPointToSave.setWarehouse(warehouseToSave);
        OrderProcessingPointValidator.validateOnSave(orderProcessingPointToSave);

        return orderProcessingPointRepository.save(orderProcessingPointToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        OrderProcessingPointValidator.validateProcessingPoint(orderProcessingPointRepository.findOne(idForDelete));
        orderProcessingPointRepository.delete(idForDelete);
    }

    @Override
    public List<OrderProcessingPoint> findAll() throws IllegalArgumentException {
        List<OrderProcessingPoint> processingPointsFromRepository = orderProcessingPointRepository.findAll();
        OrderProcessingPointValidator.validateProcessingPointList(processingPointsFromRepository);
        return processingPointsFromRepository;
    }

    @Override
    public OrderProcessingPoint findOne(long idForSearch) throws IllegalArgumentException {
        OrderProcessingPoint foundProcessingPoint = orderProcessingPointRepository.findOne(idForSearch);
        OrderProcessingPointValidator.validateProcessingPoint(foundProcessingPoint);
        return foundProcessingPoint;
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate) throws IllegalArgumentException {
        OrderProcessingPointValidator.validateProcessingPoint(orderProcessingPointRepository.findOne(processingPointDtoToUpdate.getId()));
        OrderProcessingPoint orderProcessingPointUpdate = new OrderProcessingPoint();
        orderProcessingPointUpdate.setId(processingPointDtoToUpdate.getId());
        orderProcessingPointUpdate.setLocation(processingPointDtoToUpdate.getLocation());
        orderProcessingPointUpdate.setWarehouse(modelMapper.map(processingPointDtoToUpdate.getWarehouse(), Warehouse.class));
        orderProcessingPointUpdate.setExpectedOrders(processingPointDtoToUpdate.getExpectedOrders().stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList()));
        orderProcessingPointUpdate.setDispatchedOrders(processingPointDtoToUpdate.getDispatchedOrders().stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList()));
        return orderProcessingPointRepository.update(orderProcessingPointUpdate);
    }

    @Override
    public void clear() {
        orderProcessingPointRepository.clear();
    }

}