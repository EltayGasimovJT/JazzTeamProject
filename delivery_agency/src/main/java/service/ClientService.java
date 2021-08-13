package service;

import dto.ClientDto;
import entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    void delete(Long id);

    List<Client> findAll() throws IllegalArgumentException;

    Client findById(long id) throws IllegalArgumentException;

    Client findByPassportId(String passportId);

    Client save(ClientDto clientDTO) throws SQLException;

    Client update(ClientDto clientDTO) throws SQLException;
}