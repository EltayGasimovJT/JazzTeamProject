package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView homePage(Model model) throws SQLException {
        ClientDto firstClientToTest = ClientDto.builder()
                .id(1L)
                .name("Igor")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        final Client save = clientService.save(firstClientToTest);
        model.addAttribute("Client", save);
        return new ModelAndView("index");
    }

    @GetMapping("/clients/{Id}")
    public Client findOne(
            @PathVariable Long Id, Model model
    ) {
        model.addAttribute("client", clientService.findById(Id));
        return (Client) model.getAttribute("client");
    }
}