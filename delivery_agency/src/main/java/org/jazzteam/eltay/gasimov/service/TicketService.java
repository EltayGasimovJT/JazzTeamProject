package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.TicketDto;
import org.jazzteam.eltay.gasimov.entity.Order;
import org.jazzteam.eltay.gasimov.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket findByOrderId(Order clientFroFind);

    Ticket findById(Long idForFind);

    List<Ticket> findAll();

    void delete(String id);

    Ticket save(TicketDto orderTicketToSave);

    Ticket findByTicketNumber(String ticketNumber);

    Ticket generateTicket(Long orderId);
}
