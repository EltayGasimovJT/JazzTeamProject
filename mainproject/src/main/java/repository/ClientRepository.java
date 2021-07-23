package repository;

import entity.Client;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportId);
}
