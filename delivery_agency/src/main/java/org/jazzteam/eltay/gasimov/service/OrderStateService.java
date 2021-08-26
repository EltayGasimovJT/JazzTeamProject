package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;

import java.util.List;

public interface OrderStateService {
    OrderState save(OrderStateDto orderStateDtoToSave);

    void delete(Long idForDelete);

    List<OrderState> findAll();

    OrderState findOne(long idForSearch);

    OrderState update(OrderStateDto orderStateDtoToUpdate);
}
