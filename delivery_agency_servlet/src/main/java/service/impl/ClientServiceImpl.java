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
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (clientRepository.findOne(id) == null){
                throw new IllegalArgumentException("There is no such client to delete!!!");
            }
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
            if (actualClient == null) {
                return null;
            } else {
                return fromClientToDTO(actualClient);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public ClientDto findByPassportId(String passportId) {
        ClientDto clientDto = ClientDto.builder().build();
        Client clientByPassportId;
        try {
            clientByPassportId = clientRepository.findByPassportId(passportId);
            if (clientByPassportId == null) {
                return null;
            }
            clientDto.setId(clientByPassportId.getId());
            clientDto.setName(clientByPassportId.getName());
            clientDto.setSurname(clientByPassportId.getSurname());
            clientDto.setPassportId(clientByPassportId.getPassportId());
            clientDto.setPhoneNumber(clientByPassportId.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return clientDto;
    }

    @Override
    public ClientDto save(ClientDto clientDTO) throws IllegalArgumentException {
        Client client = fromDtoToClient(clientDTO);

        try {
            if (clientRepository.findByPassportId(clientDTO.getPassportId()) != null) {
                throw new IllegalArgumentException("Clients cannot have equals passportId!!!");
            }
            client.setId(clientRepository.save(client).getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        clientDTO.setId(client.getId());
        return clientDTO;
    }

    @Override
    public ClientDto update(ClientDto clientDTO) throws IllegalArgumentException {
        Client client = fromDtoToClient(clientDTO);
        Client updateOnDB;
        try {
            if (clientRepository.findOne(clientDTO.getId()) == null) {
                throw new IllegalArgumentException("There is no client to update!!!");
            }
            if (clientRepository.findOne(clientDTO.getId()).getPassportId().equals(clientDTO.getPassportId())){
                throw new IllegalArgumentException("This passportId already exists!!!");
            }
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
