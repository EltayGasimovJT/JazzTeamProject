package org.jazzteam.eltay.gasimov.controller;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = CLIENTS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Client save(@RequestBody ClientDto clientToSave) throws ObjectNotFoundException {
        return clientService.save(clientToSave);
    }

    @GetMapping(path = CLIENTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ClientDto findById(@PathVariable Long id) throws ObjectNotFoundException {
        return modelMapper.map(clientService.findById(id), ClientDto.class);
    }

    @GetMapping(path = CLIENTS_BY_PASSPORT_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ClientDto findByPassportId(@RequestParam String passportId) throws ObjectNotFoundException {
        return modelMapper.map(clientService.findClientByPassportId(passportId), ClientDto.class);
    }

    @GetMapping(path = CLIENTS_BY_PHONE_NUMBER_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ClientDto findByPhoneNumber(@RequestParam String phoneNumber) throws ObjectNotFoundException {
        return modelMapper.map(clientService.findByPhoneNumber(phoneNumber), ClientDto.class);
    }

    @GetMapping(path = GET_GENERATED_CODE_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ClientDto getGeneratedCode(@RequestParam String phoneNumber) throws ObjectNotFoundException {
        return modelMapper.map(clientService.generateCodeForClient(phoneNumber), ClientDto.class);
    }

    @GetMapping(path = CLIENTS_BY_PASSPORT_PATH_VARIABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Set<OrderDto> findOrdersByClientPhoneNumber(@PathVariable String phoneNumber) {
        return clientService.findOrdersByClientPhoneNumber(phoneNumber);
    }

    @GetMapping(path = CLIENTS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Client> findAll() throws ObjectNotFoundException {
        return clientService.findAll();
    }

    @DeleteMapping(path = CLIENTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ObjectNotFoundException {
        clientService.delete(id);
    }

    @PutMapping(path = CLIENTS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public Client update(@RequestBody ClientDto newClient) throws ObjectNotFoundException {
        return clientService.update(newClient);
    }
}
