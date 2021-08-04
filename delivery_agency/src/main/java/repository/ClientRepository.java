package repository;

import entity.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportId);

    Client saveToDB(Client client, Connection connection) throws SQLException;

    List<Client> getFromDB(Connection connection) throws SQLException;

    void deleteFromDB(Long id, Connection connection) throws SQLException;

    Client updateOnDB(Client update, Connection connection) throws SQLException;
}
