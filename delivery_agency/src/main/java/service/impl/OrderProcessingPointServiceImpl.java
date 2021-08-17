package service.impl;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import mapping.OrderMapper;
import org.modelmapper.ModelMapper;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;
import validator.OrderProcessingPointValidator;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave) {
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
    public void delete(Long idForDelete) {
        OrderProcessingPointValidator.validateProcessingPoint(orderProcessingPointRepository.findOne(idForDelete));
        orderProcessingPointRepository.delete(idForDelete);
    }

    @Override
    public List<OrderProcessingPoint> findAll() {
        List<OrderProcessingPoint> processingPointsFromRepository = orderProcessingPointRepository.findAll();
        OrderProcessingPointValidator.validateProcessingPointList(processingPointsFromRepository);
        return processingPointsFromRepository;
    }

    @Override
    public OrderProcessingPoint findOne(long idForSearch) {
        OrderProcessingPoint foundProcessingPoint = orderProcessingPointRepository.findOne(idForSearch);
        OrderProcessingPointValidator.validateProcessingPoint(foundProcessingPoint);
        return foundProcessingPoint;
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate) {
        OrderProcessingPointValidator.validateProcessingPoint(orderProcessingPointRepository.findOne(processingPointDtoToUpdate.getId()));
        OrderProcessingPoint orderProcessingPointUpdate = new OrderProcessingPoint();
        orderProcessingPointUpdate.setId(processingPointDtoToUpdate.getId());
        orderProcessingPointUpdate.setLocation(processingPointDtoToUpdate.getLocation());
        orderProcessingPointUpdate.setWarehouse(modelMapper.map(processingPointDtoToUpdate.getWarehouse(), Warehouse.class));
        orderProcessingPointUpdate.setExpectedOrders(processingPointDtoToUpdate.getExpectedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        orderProcessingPointUpdate.setDispatchedOrders(processingPointDtoToUpdate.getDispatchedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        return orderProcessingPointRepository.update(orderProcessingPointUpdate);
    }

}