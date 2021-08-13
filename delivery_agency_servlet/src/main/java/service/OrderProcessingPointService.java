package service;

import dto.OrderProcessingPointDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPointDto addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;

    void deleteOrderProcessingPoint(Long id) throws SQLException;

    List<OrderProcessingPointDto> findAllOrderProcessingPoints() throws SQLException;

    OrderProcessingPointDto getOrderProcessingPoint(long id) throws SQLException;

    OrderProcessingPointDto update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;
}
