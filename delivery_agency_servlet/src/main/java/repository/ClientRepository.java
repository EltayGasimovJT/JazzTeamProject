package repository;

import entity.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository extends GeneralRepository<Client> {

    Client save(Client client, Connection connection) throws SQLException;

    List<Client> findAll(Connection connection) throws SQLException;

    void delete(Long id, Connection connection) throws SQLException;

    Client update(Client update, Connection connection) throws SQLException;

    Client findByPassportId(String passportID, Connection connection) throws SQLException, IllegalArgumentException;

    Client findOne(Long id, Connection connection) throws SQLException;
}
