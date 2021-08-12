package service.impl;

import dto.ClientDTO;
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
    public void delete(ClientDTO clientDTO) {
        clientRepository.delete(fromDtoToClient(clientDTO));
    }

    @Override
    public List<ClientDTO> findAllClients() {
        List<ClientDTO> resultClients = new ArrayList<>();

        List<Client> clients = new ArrayList<>(clientRepository.findAll());
        for (Client client : clients) {
            resultClients.add(fromClientToDTO(client));
        }

        return resultClients;
    }

    @Override
    public ClientDTO findById(long id) {
        Client actualClient = clientRepository.findOne(id);
        return fromClientToDTO(actualClient);
    }

    @Override
    public ClientDTO findByPassportId(String passportId) {
        ClientDTO byPassportIdDTO = ClientDTO.builder().build();
        Client clientByPassportId;
        clientByPassportId = clientRepository.findByPassportId(passportId);
        byPassportIdDTO.setId(clientByPassportId.getId());
        byPassportIdDTO.setName(clientByPassportId.getName());
        byPassportIdDTO.setSurname(clientByPassportId.getSurname());
        byPassportIdDTO.setPassportID(clientByPassportId.getPassportId());
        byPassportIdDTO.setPhoneNumber(clientByPassportId.getPhoneNumber());
        return byPassportIdDTO;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = fromDtoToClient(clientDTO);

        client.setId(clientRepository.save(client).getId());

        clientDTO.setId(client.getId());
        return clientDTO;
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        Client client = fromDtoToClient(clientDTO);
        Client updateOnDB;

        updateOnDB = clientRepository.update(client);
        return fromClientToDTO(updateOnDB);
    }

    private ClientDTO fromClientToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportID(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    private Client fromDtoToClient(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .surname(clientDTO.getSurname())
                .passportId(clientDTO.getPassportID())
                .phoneNumber(clientDTO.getPhoneNumber())
                .build();
    }
}
