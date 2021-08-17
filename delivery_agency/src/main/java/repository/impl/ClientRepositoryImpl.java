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
    public Client save(Client clientToSave) {
        clients.add(clientToSave);
        return clientToSave;
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public void delete(Long idForDelete) {
        clients.remove(findOne(idForDelete));
    }

    @Override
    public Client update(Client newClient) {
        Client clientToUpdate = findOne(newClient.getId());
        clients.remove(clientToUpdate);
        clientToUpdate.setId(newClient.getId());
        clientToUpdate.setName(newClient.getName());
        clientToUpdate.setSurname(newClient.getSurname());
        clientToUpdate.setOrders(newClient.getOrders());
        clientToUpdate.setPassportId(newClient.getPassportId());
        clientToUpdate.setPhoneNumber(newClient.getPhoneNumber());
        clients.add(clientToUpdate);
        return clientToUpdate;
    }

    @Override
    public Client findByPassportId(String passportId) throws IllegalArgumentException {
        return clients.stream()
                .filter(client -> client.getPassportId().equals(passportId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client findOne(Long idForSearch) {
        return clients.stream()
                .filter(client -> client.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }
}