package service;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint save(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;

    void delete(Long id) throws SQLException;

    List<OrderProcessingPoint> findAll() throws SQLException;

    OrderProcessingPoint findOne(long id) throws SQLException;

    OrderProcessingPoint update(OrderProcessingPointDto orderProcessingPointDto) throws SQLException;
}