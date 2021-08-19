package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;

import java.sql.SQLException;
import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave) throws SQLException;

    void delete(Long idForDelete) throws SQLException;

    List<OrderProcessingPoint> findAll() throws SQLException;

    OrderProcessingPoint findOne(long idForSearch) throws SQLException;

    OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate) throws SQLException;

    void clear();
}