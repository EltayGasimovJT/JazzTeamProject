package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;
import org.jazzteam.eltay.gasimov.repository.OrderStateRepository;
import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStateServiceImpl implements OrderStateService {
    @Autowired
    private OrderStateRepository orderStateRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderState save(OrderStateDto orderStateDtoToSave) {
        return orderStateRepository.save(modelMapper.map(orderStateDtoToSave, OrderState.class));
    }

    @Override
    public void delete(Long idForDelete) {
        orderStateRepository.deleteById(idForDelete);
    }

    @Override
    public List<OrderState> findAll() {
        return orderStateRepository.findAll();
    }

    @Override
    public OrderState findOne(long idForSearch){
        Optional<OrderState> foundOptional = orderStateRepository.findById(idForSearch);
        return foundOptional.orElseGet(OrderState::new);
    }

    @Override
    public OrderState update(OrderStateDto orderStateDtoToUpdate) {
        return orderStateRepository.save(modelMapper.map(orderStateDtoToUpdate, OrderState.class));
    }
}
