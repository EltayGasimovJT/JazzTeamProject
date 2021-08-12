package service.impl;

import dto.ClientDto;
import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void delete(Long id) {
        clientRepository.delete(id);
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<ClientDto> resultClients = new ArrayList<>();

        List<Client> clients = new ArrayList<>(clientRepository.findAll());
        for (Client client : clients) {
            resultClients.add(fromClientToDto(client));
        }

        return resultClients;
    }

    @Override
    public ClientDto findById(long id) {
        Client actualClient = clientRepository.findOne(id);
        return fromClientToDto(actualClient);
    }

    @Override
    public ClientDto findByPassportId(String passportId) {
        ClientDto byPassportIdDto = ClientDto.builder().build();
        Client clientByPassportId;
        clientByPassportId = clientRepository.findByPassportId(passportId);
        byPassportIdDto.setId(clientByPassportId.getId());
        byPassportIdDto.setName(clientByPassportId.getName());
        byPassportIdDto.setSurname(clientByPassportId.getSurname());
        byPassportIdDto.setPassportId(clientByPassportId.getPassportId());
        byPassportIdDto.setPhoneNumber(clientByPassportId.getPhoneNumber());
        return byPassportIdDto;
    }

    @Override
    public ClientDto save(ClientDto clientDTO) {
        Client client = fromDtoToClient(clientDTO);

        client.setId(clientRepository.save(client).getId());

        clientDTO.setId(client.getId());
        return clientDTO;
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        Client client = fromDtoToClient(clientDto);
        Client updateOnDB;

        updateOnDB = clientRepository.update(client);
        return fromClientToDto(updateOnDB);
    }

    private ClientDto fromClientToDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    private Client fromDtoToClient(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .passportId(clientDto.getPassportId())
                .phoneNumber(clientDto.getPhoneNumber())
                .build();
    }
}