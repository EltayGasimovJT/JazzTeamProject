package service;

import entity.Client;

public interface ClientService {
    void remindClient();

    void getPersonalDataFromClient(Client client);

    Client addClient(Client client);

    Client deleteClient(Client client);

    Client findByName(String name);

    Client findBySurName(String name);

    Client findByPassportId(String name);
}
