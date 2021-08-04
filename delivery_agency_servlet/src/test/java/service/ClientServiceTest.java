package service;


import entity.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;
import service.impl.TableServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();
    private final TableService tableService = new TableServiceImpl();

    private static Stream<Arguments> testClients() {
        Client firstClientToTest = Client.builder()
                .name("client1")
                .passportId("23612613616")
                .build();
        Client secondClientToTest = Client.builder()
                .name("client2")
                .passportId("16714714713")
                .build();
        Client thirdClientToTest = Client.builder()
                .name("client3")
                .passportId("04786533747")
                .build();

        return Stream.of(
                Arguments.of(firstClientToTest, "23612613616"),
                Arguments.of(secondClientToTest, "16714714713"),
                Arguments.of(thirdClientToTest, "04786533747")
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(Client client, String actualPassportId) {
        clientService.save(client);
        Client byPassportId = clientService.findByPassportId(actualPassportId);
        Assert.assertEquals(actualPassportId, byPassportId.getPassportId());
    }

    @Test
    void deleteClient() {
        Client firstClient = Client.builder()
                .name("firstClient")
                .passportId("23612613616")
                .build();
        Client secondClient = Client.builder()
                .name("secondClient")
                .passportId("16714714713")
                .build();
        Client thirdClient = Client.builder()
                .name("thirdClient")
                .passportId("04786533747")
                .build();

        clientService.save(firstClient);
        clientService.save(secondClient);
        clientService.save(thirdClient);

        clientService.delete(thirdClient);

        List<Client> allClients = clientService.findAllClients();

        tableService.dropTablesIfExists();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() {
        Client firstClient = Client.builder().build();
        Client secondClient = Client.builder().build();
        Client thirdClient = Client.builder().build();

        clientService.save(firstClient);
        clientService.save(secondClient);
        clientService.save(thirdClient);

        List<Client> allClients = clientService.findAllClients();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), allClients);
    }

    @Test
    void findById() {
        Client client = Client.builder()
                .name("Oleg")
                .build();
        clientService.save(client);

        Client byId = clientService.findById(1);
        Assert.assertEquals(client, byId);
    }

    @Test
    void update() {
        Client client = Client.builder()
                .name("Oleg")
                .build();
        clientService.save(client);

        client.setName("Igor");

        clientService.update(client);

        Client byId = clientService.findById(1);

        Assert.assertEquals(client, byId);
    }

    @Test
    void findByPassportId() {
        Client client = Client.builder()
                .name("Oleg")
                .passportId("12512515")
                .build();
        clientService.save(client);

        Client byId = clientService.findByPassportId("12512515");

        Assert.assertEquals("12512515", byId.getPassportId());
    }
}