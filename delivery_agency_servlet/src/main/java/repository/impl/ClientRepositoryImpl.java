package repository.impl;

import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import util.ConnectionRepositoryUtil;
import util.impl.ConnectionRepositoryUtilImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientRepositoryImpl implements ClientRepository {
    private final ConnectionRepositoryUtil connectionRepositoryUtil = new ConnectionRepositoryUtilImpl();

    @Override
    public Client save(Client client) throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO clients(name, surname, passportID, phone_number) values(?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setString(1, client.getName());
                statement.setString(2, client.getSurname());
                statement.setString(3, client.getPassportId());
                statement.setString(4, client.getPhoneNumber());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating client failed, no rows affected.");
                }
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next() && findOne(generatedKeys.getLong(1)) != null) {
                        client.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating client failed, no ID obtained.");
                    }
                }
                return client;
            }
        }
    }

    @Override
    public List<Client> findAll() throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
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
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
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
    }

    @Override
    public Client update(Client update) throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "UPDATE clients SET name = ?, surname = ?, passportID = ?, phone_number = ? WHERE id = ?;",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setString(1, update.getName());
                statement.setString(2, update.getSurname());
                statement.setString(3, update.getPassportId());
                statement.setString(4, update.getPhoneNumber());
                statement.setLong(5, update.getId());
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Creating user information failed, no rows affected.");
                }
                return update;
            }
        }
    }

    @Override
    public Client findByPassportId(String passportId) throws SQLException, IllegalArgumentException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT id, name, surname, passportID, phone_number FROM clients WHERE passportId = ?"
                    )
            ) {
                statement.setString(1, passportId);

                Client resultClient = Client.builder().build();
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        Client clientFromDB = getClient(rs);
                        resultClient.setId(clientFromDB.getId());
                        resultClient.setName(clientFromDB.getName());
                        resultClient.setSurname(clientFromDB.getSurname());
                        resultClient.setPassportId(clientFromDB.getPassportId());
                        resultClient.setPhoneNumber(clientFromDB.getPhoneNumber());
                    } else {
                        return null;
                    }
                    return resultClient;
                }
            }
        }
    }

    @Override
    public Client findOne(Long id) throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT id, name, surname, passportID, phone_number FROM clients WHERE id = ?"
                    )
            ) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        Client client = getClient(rs);
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
                .passportId(rs.getString("passportID"))
                .phoneNumber(rs.getString("phone_number"))
                .build();
    }

    @Override
    public void delete(Client client) throws SQLException {
        try (Connection connection = connectionRepositoryUtil.getConnection()) {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "DELETE FROM clients WHERE id = ?",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setLong(1, client.getId());
                statement.execute();
            }
        }
    }
}
