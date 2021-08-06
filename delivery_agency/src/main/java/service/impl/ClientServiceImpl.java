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
import java.util.ArrayList;
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
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            clientRepository.deleteFromDB(client.getId(), connection);
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Client> findAllClients() {
        List<Client> fromDB = new ArrayList<>();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            fromDB = clientRepository.getFromDB(connection);
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return fromDB;
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
    public Client saveToDB(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Client saveToDB = clientRepository.saveToDB(client, connection);
                connection.commit();
                return saveToDB;
            } catch (SQLException | NullPointerException e) {
                connection.rollback();
                log.error(e.getMessage());
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return client;
    }

    @Override
    public Client updateOnDB(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Client updateOnDB = clientRepository.updateOnDB(client, connection);
                connection.commit();
                return updateOnDB;
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
