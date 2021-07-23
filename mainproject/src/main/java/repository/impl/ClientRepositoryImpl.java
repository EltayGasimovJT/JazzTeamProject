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
    public Client findOne(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client findByPassportId(String passportId) {
        return clients.stream()
                .filter(client -> client.getPassportId().equals(passportId))
                .findFirst()
                .orElse(null);
    }
}
