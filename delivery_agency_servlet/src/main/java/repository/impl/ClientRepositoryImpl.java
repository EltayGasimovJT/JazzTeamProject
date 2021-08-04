package repository.impl;

import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class ClientRepositoryImpl implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();

    @Override
    public Client save(Client client, Connection connection) throws SQLException {
        String[] returnId = {"BATCH ID"};

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO clients(name, surname, passportID, phone_number) values(?, ?, ?, ?)",
                        returnId
                )
        ) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurName());
            statement.setString(3, client.getPassportId());
            statement.setString(4, client.getPhoneNumber());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
            return client;
        }
    }

    @Override
    public List<Client> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, surname, passportID, phone_number FROM clients"
                )
        ) {
            List<Client> clientsFromDB = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Client client = getClient(rs);
                    clientsFromDB.add(client);
                }
                return clientsFromDB;
            }
        }
    }

    @Override
    public void delete(Long id, Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM clients WHERE id = ?",
                        Statement.RETURN_GENERATED_KEYS

                )
        ) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    @Override
    public Client update(Client update, Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE clients SET name = ? WHERE id = ?;",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, update.getName());
            statement.setLong(2, update.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user information failed, no rows affected.");
            }
            return update;
        }
    }

    @Override
    public Client findByPassportId(String passportID, Connection connection) throws SQLException, IllegalArgumentException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, surname, passportID, phone_number FROM clients WHERE passportID = ?"
                )
        ) {
            statement.setString(1, passportID);

            Client resultClient = Client.builder().build();
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.getFetchSize() > 1) {
                    throw new IllegalArgumentException("Clients cannot have two same passportID!!!" + passportID);
                }
                if (rs.next()) {
                    Client clientFromDB = getClient(rs);
                    resultClient.setId(clientFromDB.getId());
                    resultClient.setName(clientFromDB.getName());
                    resultClient.setSurName(clientFromDB.getSurName());
                    resultClient.setPassportId(clientFromDB.getPassportId());
                    resultClient.setPhoneNumber(clientFromDB.getPhoneNumber());
                }
                return resultClient;
            }
        }
    }

    @Override
    public Client findOne(Long id, Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, surname, passportID, phone_number FROM clients WHERE id = ?"
                )
        ) {
            statement.setLong(1, id);

            Client client = Client.builder().build();
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    client = getClient(rs);
                }
                return client;
            }
        }
    }


    @Override
    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findOne(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client update(Client update) {
        return null;
    }


    private Client getClient(ResultSet rs) throws SQLException {
        return Client.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .surName(rs.getString("surname"))
                .passportId(rs.getString("passportID"))
                .phoneNumber(rs.getString("phone_number"))
                .build();
    }
}
