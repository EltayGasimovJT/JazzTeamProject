package service;

import entity.OrderProcessingPoint;

import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint);

    void deleteOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint);

    List<OrderProcessingPoint> findAllOrderProcessingPoints();

    OrderProcessingPoint getOrderProcessingPoint(long id);

    OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint);
}
