package servicetest.impl;

import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;
import repository.impl.OrderProcessingPointRepositoryImpl;
import servicetest.OrderProcessingPointService;

import java.util.List;

public class OrderProcessingPointServiceImpl implements OrderProcessingPointService {
    private final OrderProcessingPointRepository orderProcessingPointRepository = new OrderProcessingPointRepositoryImpl();

    @Override
    public OrderProcessingPoint addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        return orderProcessingPointRepository.save(orderProcessingPoint);
    }

    @Override
    public void deleteOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPointRepository.delete(orderProcessingPoint);
    }

    @Override
    public List<OrderProcessingPoint> findAllOrderProcessingPoints() {
        return orderProcessingPointRepository.findAll();
    }

    @Override
    public OrderProcessingPoint getOrderProcessingPoint(long id) {
        return orderProcessingPointRepository.findOne(id);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint) {
        return orderProcessingPointRepository.update(orderProcessingPoint);
    }
}
