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
    public void delete(OrderProcessingPoint orderProcessingPoint) {
        processingPoints.remove(orderProcessingPoint);
    }

    @Override
    public List<OrderProcessingPoint> findAll() {
        return processingPoints;
    }

    @Override
    public OrderProcessingPoint findOne(long id) {
        return processingPoints.stream()
                .filter(processingPoint -> processingPoint.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint update) {
        for (OrderProcessingPoint processingPoint : processingPoints) {
            if (processingPoint.getId() == update.getId()){
                processingPoint.setWarehouse(update.getWarehouse());
                processingPoint.setDispatchedOrders(update.getDispatchedOrders());
                processingPoint.setLocation(update.getLocation());
                processingPoint.setExpectedOrders(update.getExpectedOrders());
            }
        }
        return update;
    }
}
