package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;

public interface ClientRepository extends GeneralRepository<Client> {
    Client findByPassportId(String passportID) throws IllegalArgumentException;
}