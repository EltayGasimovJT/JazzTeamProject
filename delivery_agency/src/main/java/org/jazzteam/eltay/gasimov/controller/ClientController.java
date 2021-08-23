package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Client addNewUser(@RequestBody ClientDto clientToSave) throws SQLException {

        return clientService.save(clientToSave);
    }

    @GetMapping(path = "/clients")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    Iterable<Client> findAllUsers() {
        return clientService.findAll();
    }

    @DeleteMapping(path = "/clients/{id}")
    public void deleteCoefficient(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping("/clients")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Client replaceEmployee(@RequestBody ClientDto newClient) throws SQLException {
        if (clientService.findById(newClient.getId()) == null) {
            return clientService.save(newClient);
        } else {
            ClientDto clientToSave = ClientDto.builder()
                    .id(newClient.getId())
                    .name(newClient.getName())
                    .surname(newClient.getSurname())
                    .passportId(newClient.getPassportId())
                    .phoneNumber(newClient.getPhoneNumber())
                    .orders(newClient.getOrders())
                    .build();
            return clientService.update(clientToSave);
        }
    }
}
