package repository;

import entity.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportID) throws IllegalArgumentException;

    void delete(Long id) throws SQLException;
}
