package service;


import entity.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;

import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();

    private static Stream<Arguments> testClients() {
        Client client1 = Client.builder()
                .id(1L)
                .name("client1")
                .passportId("23612613616")
                .build();
        Client client2 = Client.builder()
                .id(1L)
                .name("client2")
                .passportId("16714714713")
                .build();
        Client client3 = Client.builder()
                .id(1L)
                .name("client3")
                .passportId("04786533747")
                .build();

        return Stream.of(
                Arguments.of(client1, "23612613616"),
                Arguments.of(client2, "16714714713"),
                Arguments.of(client3, "04786533747")
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(Client client, String actualPassportId) {
        clientService.addClient(client);
        Client byPassportId = clientService.findByPassportId(actualPassportId);
        Assert.assertEquals(actualPassportId, byPassportId.getPassportId());
    }

    @Test
    void deleteClient() {
        Client client1 = Client.builder()
                .id(1L)
                .name("client1")
                .passportId("23612613616")
                .build();
        Client client2 = Client.builder()
                .id(2L)
                .name("client2")
                .passportId("16714714713")
                .build();
        Client client3 = Client.builder()
                .id(3L)
                .name("client3")
                .passportId("04786533747")
                .build();

        clientService.addClient(client1);
        clientService.addClient(client2);
        clientService.addClient(client3);

        clientService.deleteClient(client3);

        List<Client> allClients = clientService.findAllClients();

        Assert.assertEquals(2, allClients.size());
    }

    @Test
    void findAllClients() {
        Client client1 = Client.builder().build();
        Client client2 = Client.builder().build();
        Client client3 = Client.builder().build();

        clientService.addClient(client1);
        clientService.addClient(client2);
        clientService.addClient(client3);

        List<Client> allClients = clientService.findAllClients();

        Assert.assertEquals(3, allClients.size());
    }

    @Test
    void findById() {
        Client client = Client.builder()
                .id(1L)
                .name("Oleg")
                .build();
        clientService.addClient(client);

        Client byId = clientService.findById(1);
        Assert.assertEquals("Oleg", byId.getName());
    }

    @Test
    void update() {
        Client client = Client.builder()
                .id(1L)
                .name("Oleg")
                .build();
        clientService.addClient(client);

        client.setName("Igor");

        clientService.update(client);

        Client byId = clientService.findById(1);

        Assert.assertEquals("Igor", byId.getName());
    }

    @Test
    void findByPassportId() {
        Client client = Client.builder()
                .id(1L)
                .name("Oleg")
                .passportId("12512515")
                .build();
        clientService.addClient(client);

        Client byId = clientService.findByPassportId("12512515");

        Assert.assertEquals("12512515", byId.getPassportId());
    }
}