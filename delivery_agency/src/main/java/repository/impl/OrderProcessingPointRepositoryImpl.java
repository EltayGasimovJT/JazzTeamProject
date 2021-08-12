package repository.impl;

import entity.OrderProcessingPoint;
import repository.OrderProcessingPointRepository;

import java.sql.SQLException;
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
        OrderProcessingPoint actual = findOne(update.getId());
        processingPoints.remove(actual);
        actual.setWarehouse(update.getWarehouse());
        actual.setDispatchedOrders(update.getDispatchedOrders());
        actual.setLocation(update.getLocation());
        actual.setExpectedOrders(update.getExpectedOrders());
        processingPoints.add(actual);
        return actual;
    }
}
