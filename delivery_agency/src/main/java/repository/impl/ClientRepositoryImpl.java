package repository.impl;

import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientRepositoryImpl implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();

    @Override
    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public void delete(Long id) {
        clients.remove(findOne(id));
    }

    @Override
    public Client update(Client update) {
        Client client = findOne(update.getId());
        clients.remove(client);
        client.setId(update.getId());
        client.setName(update.getName());
        client.setSurname(update.getSurname());
        client.setOrders(update.getOrders());
        client.setPassportId(update.getPassportId());
        client.setPhoneNumber(update.getPhoneNumber());
        clients.add(client);
        return client;
    }

    @Override
    public Client findByPassportId(String passportId) throws IllegalArgumentException {
        return clients.stream()
                .filter(client -> client.getPassportId().equals(passportId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client findOne(Long id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}