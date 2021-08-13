package service.impl;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
import org.modelmapper.ModelMapper;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.sql.SQLException;
import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override

    public OrderProcessingPoint save(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        orderProcessingPoint.setWarehouse(orderProcessingPoint.getWarehouse());
        orderProcessingPoint.setExpectedOrders(orderProcessingPoint.getExpectedOrders());
        orderProcessingPoint.setDispatchedOrders(orderProcessingPoint.getDispatchedOrders());
        return orderProcessingPointRepository.save(orderProcessingPoint);
    }

    @Override
    public void delete(Long id) {
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
    public OrderProcessingPoint update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        orderProcessingPoint.setWarehouse(orderProcessingPoint.getWarehouse());
        orderProcessingPoint.setExpectedOrders(orderProcessingPoint.getExpectedOrders());
        orderProcessingPoint.setDispatchedOrders(orderProcessingPoint.getDispatchedOrders());
        return orderProcessingPointRepository.update(orderProcessingPoint);
    }

}