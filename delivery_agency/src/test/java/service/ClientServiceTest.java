package service;

import dto.ClientDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();

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
    void testAddClient(ClientDTO clientDTO, String expectedPassportId) {
        clientService.save(clientDTO);
        ClientDTO actualClient = clientService.findByPassportId(expectedPassportId);
        Assertions.assertEquals(expectedPassportId, actualClient.getPassportID());
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

        ClientDTO clientDTO = clientService.save(firstClient);
        firstClient.setId(clientDTO.getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        clientService.delete(thirdClient);

        List<ClientDTO> allClients = clientService.findAllClients();


        Assertions.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() {
        ClientDTO firstClient = ClientDTO.builder().build();
        ClientDTO secondClient = ClientDTO.builder().build();
        ClientDTO thirdClient = ClientDTO.builder().build();

        firstClient.setId(clientService.save(firstClient).getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        List<ClientDTO> actualClients = clientService.findAllClients();


        Assertions.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), actualClients);
    }

    @Test
    void findById() {
        ClientDTO client = ClientDTO.builder()
                .id(1L)
                .name("Oleg")
                .build();

        ClientDTO savedClient = clientService.save(client);

        ClientDTO actualClient = clientService.findById(savedClient.getId());

        ClientDTO expectedClient = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportID(client.getPassportID())
                .phoneNumber(client.getPhoneNumber())
                .build();

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void update() {
        ClientDTO expectedClient = ClientDTO.builder()
                .id(1L)
                .name("Oleg")
                .build();

        ClientDTO savedClient = clientService.save(expectedClient);

        expectedClient.setId(savedClient.getId());
        expectedClient.setName("Igor");

        clientService.update(expectedClient);

        ClientDTO actualClient = clientService.findById(savedClient.getId());

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void findByPassportId() {
        String expectedPassportID = "12512515";
        ClientDTO expectedClient = ClientDTO.builder()
                .name("Oleg")
                .passportID(expectedPassportID)
                .build();

        ClientDTO save = clientService.save(expectedClient);

        expectedClient.setId(save.getId());

        ClientDTO actualClient = clientService.findByPassportId(expectedPassportID);

        Assertions.assertEquals(expectedPassportID, actualClient.getPassportID());
    }
}