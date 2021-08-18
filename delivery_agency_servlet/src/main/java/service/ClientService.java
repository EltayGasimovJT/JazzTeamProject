package service;

import dto.ClientDto;
import entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    void delete(Long idForDelete) throws SQLException;

    List<Client> findAll() throws IllegalArgumentException, SQLException;

    Client findById(long idForSearch) throws IllegalArgumentException, SQLException;

    Client findByPassportId(String passportIdForSearch) throws IllegalArgumentException, SQLException;

    Client save(ClientDto clientDtoToSave) throws SQLException, IllegalArgumentException;

    Client update(ClientDto newClient) throws SQLException, IllegalArgumentException;
}
