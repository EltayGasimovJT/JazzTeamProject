package org.jazzteam.eltay.gasimov.service.impl;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;
import org.jazzteam.eltay.gasimov.repository.ClientRepository;
import org.jazzteam.eltay.gasimov.repository.CodeRepository;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.jazzteam.eltay.gasimov.validator.ClientValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service(value = "clientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException, ObjectNotFoundException {
        Optional<Client> foundClientFromRepository = clientRepository.findById(idForDelete);
        Client foundClient = foundClientFromRepository.orElseGet(Client::new);
        ClientValidator.validateClient(foundClient);
        clientRepository.deleteById(idForDelete);
    }

    @Override
    public List<Client> findAll() throws ObjectNotFoundException {
        List<Client> foundClientsFromRepository = clientRepository.findAll();
        ClientValidator.validateClientList(foundClientsFromRepository);
        return foundClientsFromRepository;
    }

    @Override
    public Client findById(long idForSearch) throws IllegalArgumentException, ObjectNotFoundException {
        Optional<Client> foundClientFromRepository = clientRepository.findById(idForSearch);
        Client foundClient = foundClientFromRepository.orElseGet(Client::new);

        ClientValidator.validateOnFindById(foundClient, idForSearch);
        return foundClient;
    }

    @Override
    public Client findClientByPassportId(String passportIdForSearch) throws IllegalArgumentException {
        return clientRepository.findByPassportId(passportIdForSearch);
    }

    @Override
    public Client save(ClientDto clientDtoToSave) throws IllegalArgumentException, ObjectNotFoundException {
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
    public Client update(ClientDto newClient) throws IllegalArgumentException, ObjectNotFoundException {
        Optional<Client> foundClientFromRepo = clientRepository.findById(newClient.getId());
        Client foundClient = foundClientFromRepo.orElseGet(Client::new);
        ClientValidator.validateClient(foundClient);
        Client clientToUpdate = Client.builder()
                .id(newClient.getId())
                .name(newClient.getName())
                .surname(newClient.getSurname())
                .passportId(newClient.getPassportId())
                .phoneNumber(newClient.getPhoneNumber())
                .build();

        return clientRepository.save(clientToUpdate);
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) throws ObjectNotFoundException {
        Client foundClient = clientRepository.findByPhoneNumber(phoneNumber);
        ClientValidator.validateOnFindByPhoneNumber(foundClient, phoneNumber);
        return foundClient;
    }

    @Override
    public Set<OrderDto> findOrdersByClientPhoneNumber(String phoneNumber) {
        Client foundClient = clientRepository.findByPhoneNumber(phoneNumber);
        return foundClient.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Client generateCodeForClient(String phoneNumber) throws ObjectNotFoundException {
        Client foundClient = clientRepository.findByPhoneNumber(phoneNumber);
        ClientValidator.validateOnFindByPhoneNumber(foundClient, phoneNumber);
        String generatedCode = generatePersonalCode();
        ClientsCode generatedCodeObject = ClientsCode.builder()
                .client(foundClient)
                .generatedCode(generatedCode)
                .build();
        foundClient.setCode(codeRepository.save(generatedCodeObject));
        return foundClient;
    }

    @Override
    public void deleteAll() {
        clientRepository.deleteAll();
    }

    private String generatePersonalCode() {
        int randomStringLength = 4;
        String charset = "0123456789";
        return RandomStringUtils.random(randomStringLength, charset);
    }
}