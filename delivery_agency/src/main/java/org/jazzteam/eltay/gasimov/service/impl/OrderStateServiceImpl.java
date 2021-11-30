package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.Order;
import org.jazzteam.eltay.gasimov.entity.OrderState;
import org.jazzteam.eltay.gasimov.entity.Role;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.repository.OrderStateRepository;
import org.jazzteam.eltay.gasimov.service.OrderService;
import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@Service
public class OrderStateServiceImpl implements OrderStateService {
    @Autowired
    private OrderStateRepository orderStateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStateService orderStateService;

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

    @Override
    public OrderState findByState(String stateForFind) {
        return orderStateRepository.findByState(stateForFind);
    }

    @Override
    public String findStatesByRole(Worker foundByName, String orderNumber) {
        List<OrderState> allStatesFromRepository = orderStateService.findAll();
        Order foundOrder = orderService.findByTrackNumber(orderNumber);

        for (OrderState orderState : allStatesFromRepository) {
            if (foundOrder.getState().equals(orderState)) {
                if (foundByName.getRoles().iterator().next().getRole().equals(Role.ROLE_WAREHOUSE_WORKER.name())
                        && (foundOrder.getState().getId() > SEVEN && foundOrder.getState().getId() < FOUR)) {
                    throw new IllegalStateException(WAREHOUSE_NOT_ALLOWED_STATE_CHANGING_MESSAGE);
                }
                final OrderState one = orderStateService.findOne(foundOrder.getState().getNextStateId());
                return one.getState();
            }
        }
        return null;
    }
}
