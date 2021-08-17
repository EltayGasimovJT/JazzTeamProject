package service;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave) throws SQLException;

    void delete(Long idForDelete) throws SQLException;

    List<OrderProcessingPoint> findAll() throws SQLException;

    OrderProcessingPoint findOne(long idForSearch) throws SQLException;

    OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate) throws SQLException;
}