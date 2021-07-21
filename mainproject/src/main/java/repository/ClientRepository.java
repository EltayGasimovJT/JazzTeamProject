package repository;

import entity.Client;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByOrderNumber(String num);
}
