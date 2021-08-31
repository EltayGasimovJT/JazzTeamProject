package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderStateController {
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private ModelMapper modelMapper;


}
