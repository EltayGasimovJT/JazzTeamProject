package service;

import entity.Client;

import java.util.List;

public interface ClientService {
    Client addClient(Client client);

    void deleteClient(Client client);

    List<Client> findAllClients();

    Client findById(long id);

    Client update(Client client);

    Client findByPassportId(String passportId);

    Client saveToDB(Client client);

    Client updateOnDB(Client client);
}
