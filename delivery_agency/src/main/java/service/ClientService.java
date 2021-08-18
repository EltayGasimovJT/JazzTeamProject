package service;

import dto.ClientDto;
import entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    void delete(Long idForDelete);

    List<Client> findAll() throws IllegalArgumentException;

    Client findById(long idForSearch) throws IllegalArgumentException;

    Client findByPassportId(String passportIdForSearch) throws IllegalArgumentException;

    Client save(ClientDto clientDtoToSave) throws SQLException, IllegalArgumentException;

    Client update(ClientDto newClient) throws SQLException, IllegalArgumentException;
}