package service;


import dto.ClientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.ClientServiceImpl;
import util.DatabaseService;
import util.impl.DatabaseServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();
    private final DatabaseService databaseService = new DatabaseServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    private static Stream<Arguments> testClients() {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("Igor")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("13612613616")
                .orders(new ArrayList<>())
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("Alex")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23667513616")
                .orders(new ArrayList<>())
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("33612633616")
                .orders(new ArrayList<>())
                .build();

        return Stream.of(
                Arguments.of(firstClientToTest),
                Arguments.of(secondClientToTest),
                Arguments.of(thirdClientToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(ClientDto expectedClient) throws SQLException {
        ClientDto actualClient = modelMapper.map(clientService.save(expectedClient), ClientDto.class);
        expectedClient.setId(actualClient.getId());
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void deleteClient() throws SQLException {
        ClientDto firstClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23623613616")
                .build();
        ClientDto secondClient = ClientDto.builder()
                .name("Alex")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        ClientDto thirdClient = ClientDto.builder()
                .name("Igor")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23672613616")
                .build();

        databaseService.truncateTables();

        ClientDto clientDTO = modelMapper.map(clientService.save(firstClient), ClientDto.class);
        firstClient.setId(clientDTO.getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        clientService.delete(thirdClient.getId());

        List<ClientDto> allClients = clientService.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() throws SQLException {
        ClientDto firstClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("13623783616")
                .build();
        ClientDto secondClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("33623613616")
                .build();
        ClientDto thirdClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23623613616")
                .build();

        databaseService.truncateTables();

        firstClient.setId(clientService.save(firstClient).getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        List<ClientDto> actualClients = clientService.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), actualClients);
    }

    @Test
    void findById() throws SQLException {
        ClientDto client = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23623613616")
                .build();

        databaseService.truncateTables();

        ClientDto savedClient = modelMapper.map(clientService.save(client), ClientDto.class);

        ClientDto actualClient = modelMapper.map(clientService.findById(savedClient.getId()), ClientDto.class);

        ClientDto expectedClient = ClientDto.builder()
                .id(actualClient.getId())
                .name(actualClient.getName())
                .surname(actualClient.getSurname())
                .passportId(actualClient.getPassportId())
                .phoneNumber(actualClient.getPhoneNumber())
                .build();

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void update() throws SQLException {
        ClientDto expectedClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23623613616")
                .build();

        databaseService.truncateTables();

        ClientDto savedClient = modelMapper.map(clientService.save(expectedClient), ClientDto.class);

        expectedClient.setId(savedClient.getId());
        expectedClient.setName("Igor");

        clientService.update(expectedClient);

        ClientDto actualClient = modelMapper.map(clientService.findById(savedClient.getId()), ClientDto.class);

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void findByPassportId() throws SQLException {
        String expectedPassportID = "23623613616";
        ClientDto expectedClient = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23623613616")
                .build();

        databaseService.truncateTables();

        ClientDto save = modelMapper.map(clientService.save(expectedClient), ClientDto.class);

        expectedClient.setId(save.getId());

        ClientDto actualClient = modelMapper.map(clientService.findByPassportId(expectedPassportID), ClientDto.class);

        Assertions.assertEquals(expectedClient, actualClient);
    }
}
