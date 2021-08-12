package service;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPointDto addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;

    void deleteOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;

    List<OrderProcessingPointDto> findAllOrderProcessingPoints() throws SQLException;

    OrderProcessingPointDto getOrderProcessingPoint(long id) throws SQLException;

    OrderProcessingPointDto update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;
}
