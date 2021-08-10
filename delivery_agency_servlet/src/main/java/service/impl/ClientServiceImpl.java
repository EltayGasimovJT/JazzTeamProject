package service.impl;

import dto.ClientDto;
import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import service.ClientService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void delete(Long id) {
        try {
            clientRepository.delete(id);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<ClientDto> resultClients = new ArrayList<>();
        try {
            List<Client> clients = new ArrayList<>(clientRepository.findAll());
            for (Client client : clients) {
                resultClients.add(fromClientToDTO(client));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return resultClients;
    }

    @Override
    public ClientDto findById(long id) {
        try {
            Client actualClient = clientRepository.findOne(id);
            return fromClientToDTO(actualClient);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public ClientDto findByPassportId(String passportId) {
        ClientDto byPassportIdDTO = ClientDto.builder().build();
        Client clientByPassportId;
        try {
            clientByPassportId = clientRepository.findByPassportId(passportId);
            byPassportIdDTO.setId(clientByPassportId.getId());
            byPassportIdDTO.setName(clientByPassportId.getName());
            byPassportIdDTO.setSurname(clientByPassportId.getSurname());
            byPassportIdDTO.setPassportId(clientByPassportId.getPassportId());
            byPassportIdDTO.setPhoneNumber(clientByPassportId.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return byPassportIdDTO;
    }

    @Override
    public ClientDto save(ClientDto clientDTO) {
        Client client = fromDtoToClient(clientDTO);
        try {
            client.setId(clientRepository.save(client).getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        clientDTO.setId(client.getId());
        return clientDTO;
    }

    @Override
    public ClientDto update(ClientDto clientDTO) {
        Client client = fromDtoToClient(clientDTO);
        Client updateOnDB;
        try {
            updateOnDB = clientRepository.update(client);
            return fromClientToDTO(updateOnDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientDTO;
    }

    private ClientDto fromClientToDTO(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    private Client fromDtoToClient(ClientDto clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .surname(clientDTO.getSurname())
                .passportId(clientDTO.getPassportId())
                .phoneNumber(clientDTO.getPhoneNumber())
                .build();
    }
}
