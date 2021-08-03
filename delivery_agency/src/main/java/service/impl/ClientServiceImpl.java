package service.impl;

import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.ConnectionRepository;
import repository.impl.ClientRepositoryImpl;
import repository.impl.ConnectionRepositoryImpl;
import service.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();

    @Override
    public Client addClient(Client client) {
        clientRepository.save(client);
        return null;
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public List<Client> findAllClients() {
        for (Client client : clientRepository.findAll()) {
            log.info(client.toString());
        }
        return clientRepository.findAll();
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findOne(id);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public Client findByPassportId(String passportId) {
        return clientRepository.findByPassportId(passportId);
    }

    @Override
    public Client saveToDB() {
        Client client = Client.builder()
                .name("Alex")
                .surName("dads")
                .passportId("125125")
                .phoneNumber("44-756-75-35")
                .build();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                connection.commit();
                return clientRepository.saveToDB(client, connection);
            } catch (SQLException | NullPointerException e) {
                connection.rollback();
                log.error(e.getMessage());
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return client;
    }
}
