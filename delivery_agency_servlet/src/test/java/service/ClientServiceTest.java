package service;


import dto.ClientDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;
import service.impl.TableServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();
    private final TableService tableService = new TableServiceImpl();

    private static Stream<Arguments> testClients() {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("client1")
                .passportId("23612613616")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("client2")
                .passportId("16714714713")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("client3")
                .passportId("04786533747")
                .build();

        return Stream.of(
                Arguments.of(firstClientToTest),
                Arguments.of(secondClientToTest),
                Arguments.of(thirdClientToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(ClientDto expectedClient) {
        ClientDto actualClient = clientService.save(expectedClient);
        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    void deleteClient() throws SQLException {
        ClientDto firstClient = ClientDto.builder()
                .name("firstClient")
                .passportId("23612613616")
                .build();
        ClientDto secondClient = ClientDto.builder()
                .name("secondClient")
                .passportId("16714714713")
                .build();
        ClientDto thirdClient = ClientDto.builder()
                .name("thirdClient")
                .passportId("04786533747")
                .build();

        tableService.truncateTables();

        ClientDto clientDTO = clientService.save(firstClient);
        firstClient.setId(clientDTO.getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        clientService.delete(thirdClient.getId());

        List<ClientDto> allClients = clientService.findAllClients();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() throws SQLException {
        ClientDto firstClient = ClientDto.builder().name("Igor").build();
        ClientDto secondClient = ClientDto.builder().name("Eltay").build();
        ClientDto thirdClient = ClientDto.builder().name("Alex").build();

        tableService.truncateTables();

        firstClient.setId(clientService.save(firstClient).getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        List<ClientDto> actualClients = clientService.findAllClients();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), actualClients);
    }

    @Test
    void findById() throws SQLException {
        ClientDto client = ClientDto.builder()
                .name("Oleg")
                .build();

        tableService.truncateTables();

        ClientDto savedClient = clientService.save(client);

        ClientDto actualClient = clientService.findById(savedClient.getId());

        ClientDto expectedClient = ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();

        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    void update() throws SQLException {
        ClientDto expectedClient = ClientDto.builder()
                .name("Oleg")
                .build();

        tableService.truncateTables();

        ClientDto savedClient = clientService.save(expectedClient);

        expectedClient.setId(savedClient.getId());
        expectedClient.setName("Igor");

        clientService.update(expectedClient);

        ClientDto actualClient = clientService.findById(savedClient.getId());

        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    void findByPassportId() throws SQLException {
        String expectedPassportID = "12512515";
        ClientDto expectedClient = ClientDto.builder()
                .name("Oleg")
                .passportId(expectedPassportID)
                .build();

        tableService.truncateTables();

        ClientDto save = clientService.save(expectedClient);

        expectedClient.setId(save.getId());

        ClientDto actualClient = clientService.findByPassportId(expectedPassportID);

        Assert.assertEquals(expectedPassportID, actualClient.getPassportId());
    }
}