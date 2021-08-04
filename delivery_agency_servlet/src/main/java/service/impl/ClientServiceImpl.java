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
    public void delete(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            clientRepository.delete(client.getId(), connection);
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Client> findAllClients() {
        List<Client> resultClients = new ArrayList<>();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            connection.commit();
            resultClients.addAll(clientRepository.findAll(connection));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return resultClients;
    }

    @Override
    public Client findById(long id) {
        Client client = Client.builder().build();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            client = clientRepository.findOne(id);
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return client;
    }

    @Override
    public Client findByPassportId(String passportId) {
        Client byPassportId = Client.builder().build();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            Client repositoryByPassportId = clientRepository.findByPassportId(passportId, connection);
            byPassportId.setId(repositoryByPassportId.getId());
            byPassportId.setName(repositoryByPassportId.getName());
            byPassportId.setSurName(repositoryByPassportId.getSurName());
            byPassportId.setPassportId(repositoryByPassportId.getPassportId());
            byPassportId.setPhoneNumber(repositoryByPassportId.getPhoneNumber());
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return byPassportId;
    }

    @Override
    public Client save(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Client saveToDB = clientRepository.save(client, connection);
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
    public Client update(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Client updateOnDB = clientRepository.update(client, connection);
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
