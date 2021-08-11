package service.impl;

import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.sql.SQLException;
import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override
    public OrderProcessingPoint addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) throws SQLException {
        return orderProcessingPointRepository.save(orderProcessingPoint);
    }

    @Override
    public void deleteOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) throws SQLException {
        orderProcessingPointRepository.delete(orderProcessingPoint);
    }

    @Override
    public List<OrderProcessingPoint> findAllOrderProcessingPoints() throws SQLException {
        return orderProcessingPointRepository.findAll();
    }

    @Override
    public OrderProcessingPoint getOrderProcessingPoint(long id) throws SQLException {
        return orderProcessingPointRepository.findOne(id);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint) throws SQLException {
        return orderProcessingPointRepository.update(orderProcessingPoint);
    }
}
