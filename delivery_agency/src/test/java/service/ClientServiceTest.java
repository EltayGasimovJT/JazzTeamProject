package service;


import entity.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;
import util.PropertyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();

    private static Stream<Arguments> testClients() {
        Client firstClientToTest = Client.builder()
                .id(1L)
                .name("client1")
                .passportId("23612613616")
                .build();
        Client secondClientToTest = Client.builder()
                .id(1L)
                .name("client2")
                .passportId("16714714713")
                .build();
        Client thirdClientToTest = Client.builder()
                .id(1L)
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
        clientService.addClient(client);
        Client byPassportId = clientService.findByPassportId(actualPassportId);
        Assert.assertEquals(actualPassportId, byPassportId.getPassportId());
    }

    @Test
    void deleteClient() {
        Client firstClient = Client.builder()
                .id(1L)
                .name("firstClient")
                .passportId("23612613616")
                .build();
        Client secondClient = Client.builder()
                .id(2L)
                .name("secondClient")
                .passportId("16714714713")
                .build();
        Client thirdClient = Client.builder()
                .id(3L)
                .name("thirdClient")
                .passportId("04786533747")
                .build();

        clientService.addClient(firstClient);
        clientService.addClient(secondClient);
        clientService.addClient(thirdClient);

        clientService.deleteClient(thirdClient);

        List<Client> allClients = clientService.findAllClients();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() {
        Client firstClient = Client.builder().build();
        Client secondClient = Client.builder().build();
        Client thirdClient = Client.builder().build();

        clientService.addClient(firstClient);
        clientService.addClient(secondClient);
        clientService.addClient(thirdClient);

        List<Client> allClients = clientService.findAllClients();

        Assert.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), allClients);
    }

    @Test
    void findById() {
        Client client = Client.builder()
                .id(1L)
                .name("Oleg")
                .build();
        clientService.addClient(client);

        Client byId = clientService.findById(1);
        Assert.assertEquals(client, byId);
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

        Assert.assertEquals(client, byId);
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

    @Test
    void someTest(){
        clientService.saveToDB();
    }
}