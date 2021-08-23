package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;
import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderStateServiceImpl implements OrderStateService {
    @Override
    public OrderState save(OrderStateDto orderStateDtoToSave) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long idForDelete) {

    }

    @Override
    public List<OrderState> findAll() throws SQLException {
        return null;
    }

    @Override
    public OrderState findOne(long idForSearch) throws SQLException {
        return null;
    }

    @Override
    public OrderState update(OrderStateDto orderStateDtoToUpdate) throws SQLException {
        return null;
    }
}
