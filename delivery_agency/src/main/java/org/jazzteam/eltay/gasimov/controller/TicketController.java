package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.TicketDto;
import org.jazzteam.eltay.gasimov.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/generateTicket/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    TicketDto generateTicket(@PathVariable Long orderId) {
        log.severe(orderId.toString());
        return modelMapper.map(ticketService.generateTicket(orderId), TicketDto.class);
    }
}
