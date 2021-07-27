package service.impl;

import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import service.OrderProcessingPointService;

import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override
    public OrderProcessingPoint addUser(OrderProcessingPoint orderProcessingPoint) {
        return orderProcessingPointRepository.save(orderProcessingPoint);
    }

    @Override
    public void deleteUser(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPointRepository.delete(orderProcessingPoint);
    }

    @Override
    public List<OrderProcessingPoint> findAllUsers() {
        return orderProcessingPointRepository.findAll();
    }

    @Override
    public OrderProcessingPoint getUser(long id) {
        return orderProcessingPointRepository.findOne(id);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint) {
        return orderProcessingPointRepository.update(orderProcessingPoint);
    }
}
