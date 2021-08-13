package util;

import dto.ClientDto;
import entity.Client;

public class ConvertUtil {

    private ConvertUtil(){}

    public static ClientDto fromClientToDTO(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public static Client fromDtoToClient(ClientDto clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .surname(clientDTO.getSurname())
                .passportId(clientDTO.getPassportId())
                .phoneNumber(clientDTO.getPhoneNumber())
                .build();
    }
}
