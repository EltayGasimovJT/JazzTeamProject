package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByPassportId(String passportID) throws IllegalArgumentException;

    Optional<Client> findById(Long id);

    void deleteById(Long idForDelete);
}