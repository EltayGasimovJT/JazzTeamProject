package repository.impl;

import entity.Client;
import repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();

    @Override
    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findOne(int num) {
        return null;
    }

    @Override
    public Client findByOrderNumber(int passportNum) {
        Client resultClient = clients.stream()
                .filter(client -> client.getPassportId().equals(passportNum))
                .findFirst()
                .get();
        return resultClient;
    }
}
