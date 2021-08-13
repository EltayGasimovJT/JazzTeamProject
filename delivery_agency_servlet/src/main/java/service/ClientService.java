package service;

import dto.ClientDto;

import java.util.List;

public interface ClientService {
    void delete(Long id) throws IllegalArgumentException;

    List<ClientDto> findAllClients();

    ClientDto findById(long id);

    ClientDto findByPassportId(String passportId);

    ClientDto save(ClientDto clientDTO) throws IllegalArgumentException;

    ClientDto update(ClientDto clientDTO) throws IllegalArgumentException;
}
