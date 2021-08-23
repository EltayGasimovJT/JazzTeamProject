package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderHistoryDto;
import org.jazzteam.eltay.gasimov.entity.OrderHistory;

import java.sql.SQLException;
import java.util.List;

public interface OrderHistoryService {
    OrderHistory save(OrderHistoryDto orderHistoryDtoToSave) throws SQLException;

    void delete(Long idForDelete);

    List<OrderHistory> findAll() throws SQLException;

    OrderHistory findOne(long idForSearch) throws SQLException;

    OrderHistory update(OrderHistoryDto orderHistoryToUpdate) throws SQLException;
}
