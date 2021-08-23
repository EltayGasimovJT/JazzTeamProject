package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;

import java.sql.SQLException;
import java.util.List;

public interface OrderStateService {
    OrderState save(OrderStateDto orderStateDtoToSave) throws SQLException;

    void delete(Long idForDelete);

    List<OrderState> findAll() throws SQLException;

    OrderState findOne(long idForSearch) throws SQLException;

    OrderState update(OrderStateDto orderStateDtoToUpdate) throws SQLException;
}
