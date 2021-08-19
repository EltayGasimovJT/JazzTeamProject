package org.jazzteam.eltay.gasimov.repository.impl;

import org.jazzteam.eltay.gasimov.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.jazzteam.eltay.gasimov.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository(value = "clientRepository")
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
    public void clear() {
        clients.clear();
    }

    @Override
    public Client findByPassportId(String passportIdForSearch) throws IllegalArgumentException {
        return clients.stream()
                .filter(client -> client.getPassportId().equals(passportIdForSearch))
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