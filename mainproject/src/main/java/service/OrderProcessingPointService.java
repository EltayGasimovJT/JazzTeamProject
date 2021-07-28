package service;

import entity.OrderProcessingPoint;

import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint addUser(OrderProcessingPoint orderProcessingPoint);

    void deleteUser(OrderProcessingPoint orderProcessingPoint);

    List<OrderProcessingPoint> findAllUsers();

    OrderProcessingPoint getUser(long id);

    OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint);
}