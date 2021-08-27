package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;

import java.util.List;

public interface OrderProcessingPointService {
    OrderProcessingPoint save(OrderProcessingPointDto processingPointDtoToSave);

    void delete(Long idForDelete);

    List<OrderProcessingPoint> findAll();

    OrderProcessingPoint findOne(long idForSearch);

    OrderProcessingPoint update(OrderProcessingPointDto processingPointDtoToUpdate);

    OrderProcessingPoint findByLocation(String locationForFind);
}