package org.jazzteam.eltay.gasimov.service.impl;

import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.jazzteam.eltay.gasimov.dto.TicketDto;
import org.jazzteam.eltay.gasimov.entity.Order;
import org.jazzteam.eltay.gasimov.entity.Ticket;
import org.jazzteam.eltay.gasimov.repository.TicketRepository;
import org.jazzteam.eltay.gasimov.service.OrderService;
import org.jazzteam.eltay.gasimov.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public Ticket findByOrderId(Long id) {
        return ticketRepository.findByOrderId(id);
    }

    @Override
    public Ticket findById(Long idForFind) {
        Optional<Ticket> foundOptional = ticketRepository.findById(idForFind);
        return foundOptional.orElseGet(Ticket::new);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket save(TicketDto orderTicketToSave) {
        return ticketRepository.save(modelMapper.map(orderTicketToSave, Ticket.class));
    }

    @Override
    public Ticket findByTicketNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber);
    }

    @Override
    public Ticket generateTicket(Long orderId) throws ObjectNotFoundException {
        int randomStringLength = 7;
        String charset = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ";
        Order foundOrder = orderService.findOne(orderId);
        Ticket generatedTicket = Ticket.builder()
                .order(foundOrder)
                .ticketNumber(RandomStringUtils.random(randomStringLength, charset))
                .build();
        foundOrder.setTicket(generatedTicket);
        return ticketRepository.save(generatedTicket);
    }
}
