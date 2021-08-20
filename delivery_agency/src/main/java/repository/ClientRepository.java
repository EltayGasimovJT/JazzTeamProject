package repository;

import entity.Client;

import java.sql.SQLException;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportId) throws SQLException, IllegalArgumentException;
}
