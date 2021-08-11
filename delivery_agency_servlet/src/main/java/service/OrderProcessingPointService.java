package service;

import entity.OrderProcessingPoint;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) throws SQLException;

    void deleteOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) throws SQLException;

    List<OrderProcessingPoint> findAllOrderProcessingPoints() throws SQLException;

    OrderProcessingPoint getOrderProcessingPoint(long id) throws SQLException;

    OrderProcessingPoint update(OrderProcessingPoint orderProcessingPoint) throws SQLException;
}
