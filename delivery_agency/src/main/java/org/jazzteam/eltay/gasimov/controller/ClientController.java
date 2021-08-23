package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Client addNewClient(@RequestBody ClientDto clientToSave) throws SQLException {

        return clientService.save(clientToSave);
    }

    @GetMapping(path = "/clients/{id}")
    public @ResponseBody
    ClientDto findById(@PathVariable Long id) throws SQLException {
        return modelMapper.map(clientService.findById(id), ClientDto.class);
    }

    @GetMapping(path = "/clients")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Client> findAllClients() {
        return clientService.findAll();
    }

    @DeleteMapping(path = "/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public Client updateClient(@RequestBody ClientDto newClient) throws SQLException {
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