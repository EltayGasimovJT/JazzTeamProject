package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    Client addClient(Client client);

    void deleteClient(Client client);

    List<Client> showClients();

    Client getClient(long id);

    Client update(Client client);

    Client getByPassportId(String passportId);
}
