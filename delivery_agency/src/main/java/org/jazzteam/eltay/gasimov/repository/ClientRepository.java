package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByPassportId(String passportID);

    Optional<Client> findById(Long clientId);

    void deleteById(Long idForDelete);

    Client findByPhoneNumber(String phoneNumber);
}