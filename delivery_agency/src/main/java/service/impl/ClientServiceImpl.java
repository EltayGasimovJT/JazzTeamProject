package service.impl;

import dto.ClientDto;
import dto.OrderDto;
import entity.Client;
import entity.Order;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;
import service.OrderService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public void delete(Long id) {
        clientRepository.delete(id);
    }

    @Override
    public List<Client> findAll() throws IllegalArgumentException {
        List<Client> resultClients = new ArrayList<>(clientRepository.findAll());
        if (resultClients.isEmpty()) {
            throw new IllegalArgumentException("There is no clients in database!!!");
        }
        return resultClients;
    }

    @Override
    public Client findById(long id) throws IllegalArgumentException {
        Client actualClient = clientRepository.findOne(id);
        if (actualClient == null) {
            throw new IllegalArgumentException("There is no client with this Id!!!");
        }
        return actualClient;
    }

    @Override
    public Client findByPassportId(String passportId) {
        Client clientByPassportId;
        clientByPassportId = clientRepository.findByPassportId(passportId);
        if (clientByPassportId == null) {
            throw new IllegalArgumentException("There is no client with this passportId!!!");
        }
        return clientByPassportId;
    }

    @Override
    public Client save(ClientDto clientDto) throws SQLException {
        List<Order> clientOrders = new ArrayList<>();

        Client clientToSave = Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .passportId(clientDto.getPassportId())
                .phoneNumber(clientDto.getPhoneNumber())
                .build();

        Client savedClient = clientRepository.save(clientToSave);
        savedClient.setId(savedClient.getId());
        return savedClient;
    }

    @Override
    public Client update(ClientDto clientDto) throws SQLException {
        List<OrderDto> ordersToUpdate = clientDto.getOrders();

        List<Order> clientOrdersToUpdate = new ArrayList<>();

        for (OrderDto orderDto : ordersToUpdate) {
            Order orderToUpdate = Order
                    .builder()
                    .id(orderDto.getId())
                    .build();
            clientOrdersToUpdate.add(orderToUpdate);
        }

        Client clientToUpdate = Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .passportId(clientDto.getPassportId())
                .phoneNumber(clientDto.getPhoneNumber())
                .orders(clientOrdersToUpdate)
                .build();

        return clientRepository.update(clientToUpdate);
    }
}