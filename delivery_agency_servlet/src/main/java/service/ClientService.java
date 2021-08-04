package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    void delete(Client client);

    List<Client> findAllClients();

    Client findById(long id);

    Client findByPassportId(String passportId);

    Client save(Client client);

    Client update(Client client);
}
