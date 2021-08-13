package service.impl;

import dto.ClientDto;
import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.fromClientToDto;
import static util.ConvertUtil.fromDtoToClient;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void delete(Long id) {
        clientRepository.delete(id);
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<ClientDto> resultClientDtos = new ArrayList<>();

        List<Client> resultClients = new ArrayList<>(clientRepository.findAll());
        for (Client client : resultClients) {
            resultClientDtos.add(fromClientToDto(client));
        }

        return resultClientDtos;
    }

    @Override
    public ClientDto findById(long id) {
        Client actualClient = clientRepository.findOne(id);
        return fromClientToDto(actualClient);
    }

    @Override
    public ClientDto findByPassportId(String passportId) {
        ClientDto resultClientDto = ClientDto.builder().build();
        Client clientByPassportId;
        clientByPassportId = clientRepository.findByPassportId(passportId);
        resultClientDto.setId(clientByPassportId.getId());
        resultClientDto.setName(clientByPassportId.getName());
        resultClientDto.setSurname(clientByPassportId.getSurname());
        resultClientDto.setPassportId(clientByPassportId.getPassportId());
        resultClientDto.setPhoneNumber(clientByPassportId.getPhoneNumber());
        return resultClientDto;
    }

    @Override
    public ClientDto save(ClientDto clientDTO) {
        Client savedClient = fromDtoToClient(clientDTO);

        savedClient.setId(clientRepository.save(savedClient).getId());

        clientDTO.setId(savedClient.getId());
        return clientDTO;
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        Client updatedClient = fromDtoToClient(clientDto);

        return fromClientToDto(clientRepository.update(updatedClient));
    }
}