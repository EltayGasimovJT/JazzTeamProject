package service.impl;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.modelmapper.ModelMapper;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public OrderProcessingPoint save(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        Warehouse warehouseToSave = new Warehouse();
        warehouseToSave.setId(orderProcessingPointDto.getWarehouse().getId());
        warehouseToSave.setLocation(orderProcessingPointDto.getWarehouse().getLocation());
        orderProcessingPoint.setWarehouse(warehouseToSave);
        return orderProcessingPointRepository.save(orderProcessingPoint);
    }

    @Override
    public void delete(Long id) {
        if (orderProcessingPointRepository.findOne(id) == null) {
            throw new IllegalArgumentException("There is no processing Point with this Id!!! Cannot delete this client");
        }
        orderProcessingPointRepository.delete(id);
    }

    @Override
    public List<OrderProcessingPoint> findAll() {
        List<OrderProcessingPoint> processingPoints = orderProcessingPointRepository.findAll();
        if (processingPoints.isEmpty()) {
            throw new IllegalArgumentException("There is no processing Points in database!!!");
        }
        return processingPoints;
    }

    @Override
    public OrderProcessingPoint findOne(long id) {
        final OrderProcessingPoint processingPointFromRepository = orderProcessingPointRepository.findOne(id);
        if (processingPointFromRepository == null) {
            throw new IllegalArgumentException("There is no processing Point wih such Id!!!");
        }
        return processingPointFromRepository;
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        orderProcessingPoint.setWarehouse(modelMapper.map(orderProcessingPointDto.getWarehouse(), Warehouse.class));
        //orderProcessingPoint.setExpectedOrders(orderProcessingPointDto.getExpectedOrders());
        //orderProcessingPoint.setDispatchedOrders(orderProcessingPointDto.getDispatchedOrders());
        return orderProcessingPointRepository.update(orderProcessingPoint);
    }

}