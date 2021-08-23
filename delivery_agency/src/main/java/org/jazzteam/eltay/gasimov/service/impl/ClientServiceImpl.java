package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jazzteam.eltay.gasimov.repository.ClientRepository;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.jazzteam.eltay.gasimov.validator.ClientValidator;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service(value = "clientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Client> foundClientFromRepository = clientRepository.findById(idForDelete);
        Client foundClient = foundClientFromRepository.orElseGet(Client::new);
        ClientValidator.validateClient(foundClient);
        clientRepository.deleteById(idForDelete);
    }

    @Override
    public List<Client> findAll() throws IllegalArgumentException {
        List<Client> foundClientsFromRepository = clientRepository.findAll();
        ClientValidator.validateClientList(foundClientsFromRepository);
        return foundClientsFromRepository;
    }

    @Override
    public Client findById(long idForSearch) throws IllegalArgumentException {
        Optional<Client> foundClientFromRepository = clientRepository.findById(idForSearch);
        Client foundClient = foundClientFromRepository.orElseGet(Client::new);

        ClientValidator.validateClient(foundClient);
        return foundClient;
    }

    @Override
    public Client findByPassportId(String passportIdForSearch) throws IllegalArgumentException {
        Client foundClientFromRepository = clientRepository.findByPassportId(passportIdForSearch);
        ClientValidator.validateClient(foundClientFromRepository);
        return foundClientFromRepository;
    }

    @Override
    public Client save(ClientDto clientDtoToSave) throws IllegalArgumentException {
        Client clientToSave = Client.builder()
                .id(clientDtoToSave.getId())
                .name(clientDtoToSave.getName())
                .surname(clientDtoToSave.getSurname())
                .passportId(clientDtoToSave.getPassportId())
                .phoneNumber(clientDtoToSave.getPhoneNumber())
                .build();

        ClientValidator.validateOnSave(clientToSave);

        Client savedClient = clientRepository.save(clientToSave);

        savedClient.setId(savedClient.getId());
        return savedClient;
    }

    @Override
    public Client update(ClientDto newClient) throws SQLException, IllegalArgumentException {
        Optional<Client> foundClientFromRepo = clientRepository.findById(newClient.getId());
        Client foundClient = foundClientFromRepo.orElseGet(Client::new);
        ClientValidator.validateClient(foundClient);
        Set<Order> clientOrdersToUpdate = new HashSet<>();
        if (newClient.getOrders() != null) {
            clientOrdersToUpdate = newClient.getOrders().stream()
                    .map(CustomModelMapper::mapDtoToOrder)
                    .collect(Collectors.toSet());
        }

        Client clientToUpdate = Client.builder()
                .id(newClient.getId())
                .name(newClient.getName())
                .surname(newClient.getSurname())
                .passportId(newClient.getPassportId())
                .phoneNumber(newClient.getPhoneNumber())
                .orders(clientOrdersToUpdate)
                .build();

        return clientRepository.save(clientToUpdate);
    }

}