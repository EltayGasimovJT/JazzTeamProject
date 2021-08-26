package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderHistoryDto;
import org.jazzteam.eltay.gasimov.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    OrderHistory save(OrderHistoryDto orderHistoryDtoToSave);

    void delete(Long idForDelete);

    List<OrderHistory> findAll();

    OrderHistory findOne(long idForSearch);

    OrderHistory update(OrderHistoryDto orderHistoryToUpdate);
}
