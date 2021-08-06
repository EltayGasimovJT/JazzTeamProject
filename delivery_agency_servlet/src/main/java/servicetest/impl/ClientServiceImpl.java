package servicetest.impl;

import dto.ClientDTO;
import entity.Client;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;
import servicetest.ClientService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void delete(ClientDTO clientDTO) {
        clientRepository.delete(fromDtoToClient(clientDTO));
    }

    @Override
    public List<ClientDTO> findAllClients() {
        List<ClientDTO> resultClients = new ArrayList<>();
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
    public ClientDTO findById(long id) {
        ClientDTO clientDTO = ClientDTO.builder().build();
        Client actualClient;
        try {
            actualClient = clientRepository.findOne(id);
            clientDTO.setId(actualClient.getId());
            clientDTO.setName(actualClient.getName());
            clientDTO.setSurname(actualClient.getSurname());
            clientDTO.setPassportID(actualClient.getPassportID());
            clientDTO.setPhoneNumber(actualClient.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return clientDTO;
    }

    @Override
    public ClientDTO findByPassportId(String passportId) {
        ClientDTO byPassportIdDTO = ClientDTO.builder().build();
        Client clientByPassportId;
        try {
            clientByPassportId = clientRepository.findByPassportId(passportId);
            byPassportIdDTO.setId(clientByPassportId.getId());
            byPassportIdDTO.setName(clientByPassportId.getName());
            byPassportIdDTO.setSurname(clientByPassportId.getSurname());
            byPassportIdDTO.setPassportID(clientByPassportId.getPassportID());
            byPassportIdDTO.setPhoneNumber(clientByPassportId.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return byPassportIdDTO;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = fromDtoToClient(clientDTO);
        Client saveToDB;
        try {
            saveToDB = clientRepository.save(client);
            return fromClientToDTO(saveToDB);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return clientDTO;
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
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

    private ClientDTO fromClientToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportID(client.getPassportID())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    private Client fromDtoToClient(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .surname(clientDTO.getSurname())
                .passportID(clientDTO.getPassportID())
                .phoneNumber(clientDTO.getPhoneNumber())
                .build();
    }
}
