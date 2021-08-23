package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderHistoryDto;
import org.jazzteam.eltay.gasimov.entity.OrderHistory;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.OrderHistoryRepository;
import org.jazzteam.eltay.gasimov.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public OrderHistory save(OrderHistoryDto orderHistoryDtoToSave) throws SQLException {
        OrderHistory orderHistoryToSave = OrderHistory.builder()
                .changedAt(orderHistoryDtoToSave.getChangingTime())
                .comment(orderHistoryDtoToSave.getComment())
                .changedTypeEnum(orderHistoryDtoToSave.getChangedTypeEnum().toString())
                .user(CustomModelMapper.mapDtoToUser(orderHistoryDtoToSave.getUser()))
                .build();
        return orderHistoryRepository.save(orderHistoryToSave);
    }

    @Override
    public void delete(Long idForDelete) {
        orderHistoryRepository.deleteById(idForDelete);
    }

    @Override
    public List<OrderHistory> findAll() throws SQLException {
        return orderHistoryRepository.findAll();
    }

    @Override
    public OrderHistory findOne(long idForSearch) throws SQLException {
        Optional<OrderHistory> foundClientFromRepository = orderHistoryRepository.findById(idForSearch);
        return foundClientFromRepository.orElseGet(OrderHistory::new);
    }

    @Override
    public OrderHistory update(OrderHistoryDto orderHistoryToUpdate) throws SQLException {
        Optional<OrderHistory> foundClientFromRepository = orderHistoryRepository.findById(orderHistoryToUpdate.getId());
        OrderHistory foundHistory = foundClientFromRepository.orElseGet(OrderHistory::new);
        foundHistory.setChangedAt(orderHistoryToUpdate.getChangingTime());
        foundHistory.setComment(orderHistoryToUpdate.getComment());
        foundHistory.setChangedTypeEnum(orderHistoryToUpdate.getChangedTypeEnum().toString());
        foundHistory.setUser(CustomModelMapper.mapDtoToUser(orderHistoryToUpdate.getUser()));
        return orderHistoryRepository.save(foundHistory);
    }
}
