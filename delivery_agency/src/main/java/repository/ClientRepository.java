package repository;

import entity.Client;

import java.sql.Connection;
import java.sql.SQLException;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportId);

    Client saveToDB(Client client, Connection connection) throws SQLException;
}
