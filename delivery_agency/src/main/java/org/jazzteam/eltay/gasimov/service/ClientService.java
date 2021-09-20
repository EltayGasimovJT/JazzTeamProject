package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;

import java.util.List;
import java.util.Set;

public interface ClientService {
    void delete(Long idForDelete) throws ObjectNotFoundException;

    List<Client> findAll() throws IllegalArgumentException, ObjectNotFoundException;

    Client findById(long idForSearch) throws IllegalArgumentException, ObjectNotFoundException;

    Client findClientByPassportId(String passportIdForSearch) throws IllegalArgumentException, ObjectNotFoundException;

    Client save(ClientDto clientDtoToSave) throws IllegalArgumentException, ObjectNotFoundException;

    Client update(ClientDto newClient) throws IllegalArgumentException, ObjectNotFoundException;

    OrderProcessingPoint determineCurrentDestinationPlace(String destinationPlace);

    Client findByPhoneNumber(String phoneNumber) throws ObjectNotFoundException;

    Set<OrderDto> findOrdersByClientPhoneNumber(String phoneNumber);

    Client generateCodeForClient(String phoneNumber) throws ObjectNotFoundException;
}