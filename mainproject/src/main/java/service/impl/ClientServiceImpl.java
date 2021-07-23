package service.impl;

import entity.Client;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;

import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();


    @Override
    public Client addClient(Client client) {
        clientRepository.save(client);
        return null;
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public List<Client> showClients() {
        for (Client client : clientRepository.findAll()) {
            log.info(client.toString());
        }
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(long id) {
        return null;
    }

    @Override
    public Client update(Client Client) {
        return null;
    }

    @Override
    public Client getByPassportId(String passportId) {
        return null;
    }
}
