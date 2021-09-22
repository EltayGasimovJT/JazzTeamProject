package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.TicketDto;
import org.jazzteam.eltay.gasimov.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket findByOrderId(Long orderId);

    Ticket findById(Long idForFind);

    List<Ticket> findAll();

    void delete(Long id);

    Ticket save(TicketDto orderTicketToSave);

    Ticket findByTicketNumber(String ticketNumber);

    Ticket generateTicket(Long orderId) throws ObjectNotFoundException;
}
