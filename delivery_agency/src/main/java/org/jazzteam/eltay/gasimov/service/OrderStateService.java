package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;
import org.jazzteam.eltay.gasimov.entity.Worker;

import java.util.List;

public interface OrderStateService {
    OrderState save(OrderStateDto orderStateDtoToSave);

    void delete(Long idForDelete);

    List<OrderState> findAll();

    OrderState findOne(long idForSearch);

    OrderState update(OrderStateDto orderStateDtoToUpdate);

    OrderState findByState(String toString);

    String findStatesByRole(Worker foundByName, String orderNumber);
}
