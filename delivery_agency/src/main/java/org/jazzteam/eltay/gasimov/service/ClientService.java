package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;

import java.util.List;
import java.util.Set;

public interface ClientService {
    void delete(Long idForDelete);

    List<Client> findAll() throws IllegalArgumentException;

    Client findById(long idForSearch) throws IllegalArgumentException;

    Client findClientByPassportId(String passportIdForSearch) throws IllegalArgumentException;

    Client save(ClientDto clientDtoToSave) throws IllegalArgumentException;

    Client update(ClientDto newClient) throws IllegalArgumentException;

    OrderProcessingPoint determineCurrentDestinationPlace(String destinationPlace);

    Client findByPhoneNumber(String phoneNumber);

    Set<OrderDto> findOrdersByClientPhoneNumber(String phoneNumber);
}