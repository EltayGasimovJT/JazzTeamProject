package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<ClientsCode, Long> {
    ClientsCode findByClient(Client clientForFind);
    ClientsCode save(ClientsCode code);

    ClientsCode findByGeneratedCode(String code);

    void deleteByGeneratedCode(String code);
}
