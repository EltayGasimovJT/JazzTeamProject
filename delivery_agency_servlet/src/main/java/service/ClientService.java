package service;

import dto.ClientDTO;

import java.util.List;

public interface ClientService {
    void delete(ClientDTO clientDTO);

    List<ClientDTO> findAllClients();

    ClientDTO findById(long id);

    ClientDTO findByPassportId(String passportId);

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO update(ClientDTO clientDTO);
}
