package service;

import dto.ClientDto;

import java.util.List;

public interface ClientService {
    void delete(ClientDto clientDTO);

    List<ClientDto> findAllClients();

    ClientDto findById(long id);

    ClientDto findByPassportId(String passportId);

    ClientDto save(ClientDto clientDTO);

    ClientDto update(ClientDto clientDTO);
}
