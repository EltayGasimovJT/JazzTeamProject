package org.jazzteam.eltay.gasimov.service;


import org.jazzteam.eltay.gasimov.dto.ClientsCodeDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;

import java.util.List;

public interface CodeService {
    ClientsCode findByClientId(Client clientFroFind);

    ClientsCode findById(Long idForFind);

    List<ClientsCode> findAll();

    void delete(String code);

    ClientsCode save(ClientsCodeDto clientsCode);

    ClientsCode findByCode(String code);
}
