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
        Client actual = findOne(update.getId());
        clients.remove(actual);
        actual.setId(update.getId());
        actual.setName(update.getName());
        actual.setSurname(update.getSurname());
        actual.setOrders(update.getOrders());
        actual.setPassportId(update.getPassportId());
        actual.setPhoneNumber(update.getPhoneNumber());
        clients.add(actual);
        return actual;
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

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }
}
