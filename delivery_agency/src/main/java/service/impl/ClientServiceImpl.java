package service.impl;

import dto.ClientDto;
import entity.Client;
import entity.Order;
import lombok.extern.slf4j.Slf4j;
import mapping.OrderMapper;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;
import validator.ClientValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        ClientValidator.validateClient(clientRepository.findOne(idForDelete));
        clientRepository.delete(idForDelete);
    }

    @Override
    public List<Client> findAll() throws IllegalArgumentException {
        List<Client> foundClientsFromRepository = clientRepository.findAll();
        ClientValidator.validateClientList(foundClientsFromRepository);
        return foundClientsFromRepository;
    }

    @Override
    public Client findById(long idForSearch) throws IllegalArgumentException {
        Client foundClientFromRepository = clientRepository.findOne(idForSearch);
        ClientValidator.validateClient(foundClientFromRepository);
        return foundClientFromRepository;
    }

    @Override
    public Client findByPassportId(String passportIdForSearch) throws IllegalArgumentException {
        Client foundClientFromRepository = clientRepository.findByPassportId(passportIdForSearch);
        ClientValidator.validateClient(foundClientFromRepository);
        return foundClientFromRepository;
    }

    @Override
    public Client save(ClientDto clientDtoToSave) throws SQLException, IllegalArgumentException {
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
        ClientValidator.validateClient(clientRepository.findOne(newClient.getId()));
        List<Order> clientOrdersToUpdate = newClient.getOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList());

        Client clientToUpdate = Client.builder()
                .id(newClient.getId())
                .name(newClient.getName())
                .surname(newClient.getSurname())
                .passportId(newClient.getPassportId())
                .phoneNumber(newClient.getPhoneNumber())
                .orders(clientOrdersToUpdate)
                .build();

        return clientRepository.update(clientToUpdate);
    }
}