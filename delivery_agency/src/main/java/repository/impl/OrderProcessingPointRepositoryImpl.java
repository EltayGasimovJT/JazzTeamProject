package repository.impl;

import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessingPointRepositoryImpl implements OrderProcessingPointRepository {
    private final List<OrderProcessingPoint> processingPoints = new ArrayList<>();

    @Override
    public OrderProcessingPoint save(OrderProcessingPoint orderProcessingPoint) {
        processingPoints.add(orderProcessingPoint);
        return orderProcessingPoint;
    }

    @Override
    public void delete(Long id) {
        processingPoints.remove(findOne(id));
    }

    @Override
    public List<OrderProcessingPoint> findAll() {
        return processingPoints;
    }

    @Override
    public OrderProcessingPoint findOne(Long id) {
        return processingPoints.stream()
                .filter(processingPoint -> processingPoint.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint update)  {
        OrderProcessingPoint orderProcessingPoint = findOne(update.getId());
        processingPoints.remove(orderProcessingPoint);
        orderProcessingPoint.setWarehouse(update.getWarehouse());
        orderProcessingPoint.setDispatchedOrders(update.getDispatchedOrders());
        orderProcessingPoint.setLocation(update.getLocation());
        orderProcessingPoint.setExpectedOrders(update.getExpectedOrders());
        processingPoints.add(orderProcessingPoint);
        return orderProcessingPoint;
    }
}