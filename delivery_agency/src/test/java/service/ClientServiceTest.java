package service;

import dto.ClientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.ClientServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ClientServiceTest {
    private final ClientService clientService = new ClientServiceImpl();

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
                Arguments.of(firstClientToTest, "23612613616"),
                Arguments.of(secondClientToTest, "16714714713"),
                Arguments.of(thirdClientToTest, "04786533747")
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(ClientDto expectedClient, String expectedPassportId) {
        clientService.save(expectedClient);
        ClientDto actualClient = clientService.findByPassportId(expectedPassportId);
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void deleteClient() {
        ClientDto firstClient = ClientDto.builder()
                .id(1L)
                .name("firstClient")
                .passportId("23612613616")
                .build();
        ClientDto secondClient = ClientDto.builder()
                .id(2L)
                .name("secondClient")
                .passportId("16714714713")
                .build();
        ClientDto thirdClient = ClientDto.builder()
                .id(3L)
                .name("thirdClient")
                .passportId("04786533747")
                .build();

        ClientDto clientDTO = clientService.save(firstClient);
        firstClient.setId(clientDTO.getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        clientService.delete(thirdClient.getId());

        List<ClientDto> allClients = clientService.findAll();


        Assertions.assertEquals(Arrays.asList(firstClient, secondClient), allClients);
    }

    @Test
    void findAllClients() {
        ClientDto firstClient = ClientDto.builder().build();
        ClientDto secondClient = ClientDto.builder().build();
        ClientDto thirdClient = ClientDto.builder().build();

        firstClient.setId(clientService.save(firstClient).getId());
        secondClient.setId(clientService.save(secondClient).getId());
        thirdClient.setId(clientService.save(thirdClient).getId());

        List<ClientDto> actualClients = clientService.findAll();


        Assertions.assertEquals(Arrays.asList(firstClient, secondClient, thirdClient), actualClients);
    }

    @Test
    void findById() {
        ClientDto client = ClientDto.builder()
                .id(1L)
                .name("Oleg")
                .build();

        ClientDto savedClient = clientService.save(client);

        ClientDto actualClient = clientService.findById(savedClient.getId());

        ClientDto expectedClient = ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void update() {
        ClientDto expectedClient = ClientDto.builder()
                .id(1L)
                .name("Oleg")
                .build();

        ClientDto savedClient = clientService.save(expectedClient);

        expectedClient.setId(savedClient.getId());
        expectedClient.setName("Igor");

        clientService.update(expectedClient);

        ClientDto actualClient = clientService.findById(savedClient.getId());

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void findByPassportId() {
        String expectedPassportID = "12512515";
        ClientDto expectedClient = ClientDto.builder()
                .name("Oleg")
                .passportId(expectedPassportID)
                .build();

        ClientDto save = clientService.save(expectedClient);

        expectedClient.setId(save.getId());

        ClientDto actualClient = clientService.findByPassportId(expectedPassportID);

        Assertions.assertEquals(expectedClient, actualClient);
    }
}