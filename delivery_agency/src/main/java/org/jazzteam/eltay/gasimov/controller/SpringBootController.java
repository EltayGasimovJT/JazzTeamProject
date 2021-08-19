package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Slf4j
public class SpringBootController {
    @Autowired
    private ClientService clientService;


    @GetMapping("/delivery_agency")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/")
    public String homePage(Model model) throws SQLException {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("Igor")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        clientService.save(firstClientToTest);
        return clientService.findAll().toString();
    }
}