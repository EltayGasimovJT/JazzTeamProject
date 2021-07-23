package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    Client addClient(Client Client);

    void deleteClient(Client Client);

    List<Client> showClients();

    Client getClient(long id);

    Client update(Client Client);

    Client getByPassportId(String passportId);
}
