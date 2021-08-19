package org.jazzteam.eltay.gasimov.repository.impl;

import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.springframework.stereotype.Repository;
import org.jazzteam.eltay.gasimov.repository.OrderProcessingPointRepository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "orderProcessingPointRepository")
public class OrderProcessingPointRepositoryImpl implements OrderProcessingPointRepository {
    private final List<OrderProcessingPoint> processingPoints = new ArrayList<>();

    @Override
    public OrderProcessingPoint save(OrderProcessingPoint processingPointToSave) {
        processingPoints.add(processingPointToSave);
        return processingPointToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        processingPoints.remove(findOne(idForDelete));
    }

    @Override
    public List<OrderProcessingPoint> findAll() {
        return processingPoints;
    }

    @Override
    public OrderProcessingPoint findOne(Long idForSearch) {
        return processingPoints.stream()
                .filter(processingPoint -> processingPoint.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public OrderProcessingPoint update(OrderProcessingPoint newProcessingPoint)  {
        OrderProcessingPoint processingPointToUpdate = findOne(newProcessingPoint.getId());
        processingPoints.remove(processingPointToUpdate);
        processingPointToUpdate.setWarehouse(newProcessingPoint.getWarehouse());
        processingPointToUpdate.setDispatchedOrders(newProcessingPoint.getDispatchedOrders());
        processingPointToUpdate.setLocation(newProcessingPoint.getLocation());
        processingPointToUpdate.setExpectedOrders(newProcessingPoint.getExpectedOrders());
        processingPoints.add(processingPointToUpdate);
        return processingPointToUpdate;
    }
}