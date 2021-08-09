package repository.impl;

import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.ConnectionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientRepositoryImpl implements ClientRepository {
    private final ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();

    @Override
    public Client save(Client client) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO clients(name, surname, passportID, phone_number) values(?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setString(1, client.getName());
                statement.setString(2, client.getSurname());
                statement.setString(3, client.getPassportID());
                statement.setString(4, client.getPhoneNumber());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Creating client failed, no rows affected.");
                }
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    connection.commit();
                    if (generatedKeys.next()) {
                        client.setId(generatedKeys.getLong(1));
                    } else {
                        connection.rollback();
                        throw new SQLException("Creating client failed, no ID obtained.");
                    }
                }
                return client;
            }
        }
    }

    @Override
    public List<Client> findAll() throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
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
                    connection.commit();
                    return clientsFromDB;
                }
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "DELETE FROM clients WHERE id = ?",
                            Statement.RETURN_GENERATED_KEYS

                    )
            ) {
                statement.setLong(1, id);
                statement.execute();
                connection.commit();
            }
        }
    }

    @Override
    public Client update(Client update) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "UPDATE clients SET name = ? WHERE id = ?;",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setString(1, update.getName());
                statement.setLong(2, update.getId());
                int affectedRows = statement.executeUpdate();
                connection.commit();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Creating user information failed, no rows affected.");
                }
                return update;
            }
        }
    }

    @Override
    public Client findByPassportId(String passportID) throws SQLException, IllegalArgumentException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT id, name, surname, passportID, phone_number FROM clients WHERE passportID = ?"
                    )
            ) {
                statement.setString(1, passportID);

                Client resultClient = Client.builder().build();
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        Client clientFromDB = getClient(rs);
                        resultClient.setId(clientFromDB.getId());
                        resultClient.setName(clientFromDB.getName());
                        resultClient.setSurname(clientFromDB.getSurname());
                        resultClient.setPassportID(clientFromDB.getPassportID());
                        resultClient.setPhoneNumber(clientFromDB.getPhoneNumber());
                    }
                    connection.commit();
                    return resultClient;
                }
            }
        }
    }

    @Override
    public Client findOne(Long id) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT id, name, surname, passportID, phone_number FROM clients WHERE id = ?"
                    )
            ) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        Client client = getClient(rs);
                        connection.commit();
                        return client;
                    }
                }
            }
        }
        return null;
    }

    private Client getClient(ResultSet rs) throws SQLException {
        return Client.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .passportID(rs.getString("passportID"))
                .phoneNumber(rs.getString("phone_number"))
                .build();
    }

    @Override
    public void delete(Client client) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "DELETE FROM clients WHERE id = ?",
                            Statement.RETURN_GENERATED_KEYS

                    )
            ) {
                statement.setLong(1, client.getId());
                statement.execute();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
