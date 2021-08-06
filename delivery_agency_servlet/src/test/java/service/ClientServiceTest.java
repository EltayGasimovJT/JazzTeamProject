package service;


import dto.ClientDTO;
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
        ClientDTO firstClientToTest = ClientDTO.builder()
                .name("client1")
                .passportID("23612613616")
                .build();
        ClientDTO secondClientToTest = ClientDTO.builder()
                .name("client2")
                .passportID("16714714713")
                .build();
        ClientDTO thirdClientToTest = ClientDTO.builder()
                .name("client3")
                .passportID("04786533747")
                .build();

        return Stream.of(
                Arguments.of(firstClientToTest, "23612613616"),
                Arguments.of(secondClientToTest, "16714714713"),
                Arguments.of(thirdClientToTest, "04786533747")
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(ClientDTO clientDTO, String actualPassportId) {
        clientService.save(clientDTO);
        ClientDTO byPassportId = clientService.findByPassportId(actualPassportId);
        Assert.assertEquals(actualPassportId, byPassportId.getPassportID());
    }

    @Test
    void deleteClient() {
        ClientDTO firstClient = ClientDTO.builder()
                .name("firstClient")
                .passportID("23612613616")
                .build();
        ClientDTO secondClient = ClientDTO.builder()
                .name("secondClient")
                .passportID("16714714713")
                .build();
        ClientDTO thirdClient = ClientDTO.builder()
                .name("thirdClient")
                .passportID("04786533747")
                .build();

        tableService.truncateTables();

        ClientDTO clientDTO = clientService.save(firstClient);
        firstClient.setId(clientDTO.getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        clientService.delete(thirdClient);

        List<ClientDTO> allClients = clientService.findAllClients();


        Assert.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() {
        ClientDTO firstClient = ClientDTO.builder().build();
        ClientDTO secondClient = ClientDTO.builder().build();
        ClientDTO thirdClient = ClientDTO.builder().build();

        tableService.truncateTables();

        firstClient.setId(clientService.save(firstClient).getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        List<ClientDTO> allClients = clientService.findAllClients();



        Assert.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), allClients);
    }

    @Test
    void findById() {
        ClientDTO client = ClientDTO.builder()
                .id(1L)
                .name("Oleg")
                .build();

        tableService.truncateTables();

        clientService.save(client);

        ClientDTO actualClient = clientService.findById(1);

        ClientDTO expectedClient = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportID(client.getPassportID())
                .phoneNumber(client.getPhoneNumber())
                .build();

        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    void update() {
        ClientDTO expectedClient = ClientDTO.builder()
                .name("Oleg")
                .build();

        tableService.truncateTables();

        ClientDTO savedClient = clientService.save(expectedClient);

        expectedClient.setId(savedClient.getId());
        expectedClient.setName("Igor");

        clientService.update(expectedClient);

        ClientDTO actualClient = clientService.findById(1);

        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    void findByPassportId() {
        String expectedPassportID = "12512515";
        ClientDTO expectedClient = ClientDTO.builder()
                .name("Oleg")
                .passportID(expectedPassportID)
                .build();

        tableService.truncateTables();

        ClientDTO save = clientService.save(expectedClient);

        expectedClient.setId(save.getId());

        ClientDTO actualClient = clientService.findByPassportId(expectedPassportID);

        Assert.assertEquals(expectedPassportID, actualClient.getPassportID());
    }
}