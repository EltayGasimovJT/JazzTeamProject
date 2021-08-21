package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    private static Stream<Arguments> testClients() {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("Igor")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("Alex")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("16714714713")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("125125")
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
    void testAddClient(ClientDto expectedClientDto, String expectedPassportId) throws SQLException {
        clientService.save(expectedClientDto);
        Client actualClient = clientService.findByPassportId(expectedPassportId);
        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);
        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void deleteClient() throws SQLException {
        ClientDto firstClientToTest = ClientDto.builder()
                .id(1L)
                .name("firstClient")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .id(2L)
                .name("secondClient")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("16714714713")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .id(3L)
                .name("thirdClient")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("04786533747")
                .build();

        clientService.clear();

        firstClientToTest.setId(clientService.save(firstClientToTest).getId());
        secondClientToTest.setId(clientService.save(secondClientToTest).getId());
        thirdClientToTest.setId(clientService.save(thirdClientToTest).getId());

        clientService.delete(thirdClientToTest.getId());

        List<Client> actualClients = clientService.findAll();
        List<ClientDto> actualClientDtos = actualClients.stream()
                .map(actualClientDto -> modelMapper.map(actualClientDto, ClientDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(firstClientToTest, secondClientToTest), actualClientDtos);
    }

    @Test
    void findAllClients() throws SQLException {
        ClientDto firstClientToTest = ClientDto.builder()
                .id(1L)
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .id(2L)
                .name("secondClient")
                .surname("Gleb")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .id(3L)
                .name("thirdClient")
                .surname("igor")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        clientService.clear();

        firstClientToTest.setId(clientService.save(firstClientToTest).getId());
        secondClientToTest.setId(clientService.save(secondClientToTest).getId());
        thirdClientToTest.setId(clientService.save(thirdClientToTest).getId());

        List<Client> actualClients = clientService.findAll();

        List<ClientDto> actualClientDtos = actualClients.stream()
                .map(actualClientDto -> modelMapper.map(actualClientDto, ClientDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(firstClientToTest, secondClientToTest, thirdClientToTest), actualClientDtos);
    }

    @Test
    void findById() throws SQLException {
        ClientDto expectedClientDto = ClientDto.builder()
                .id(1L)
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("125125")
                .passportId("23612613616")
                .build();
        clientService.clear();

        Client savedClient = clientService.save(expectedClientDto);

        Client actualClient = clientService.findById(savedClient.getId());

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void update() throws SQLException {
        ClientDto expectedClientDto = ClientDto.builder()
                .id(1L)
                .name("Oleg")
                .surname("Vasya")
                .phoneNumber("125125")
                .passportId("23612613616")
                .orders(new HashSet<>())
                .build();
        clientService.clear();

        Client savedClient = clientService.save(expectedClientDto);

        expectedClientDto.setId(savedClient.getId());
        expectedClientDto.setName("Igor");

        clientService.update(expectedClientDto);

        Client actualClient = clientService.findById(savedClient.getId());

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void findByPassportId() throws SQLException {
        String expectedPassportID = "12512515";
        ClientDto expectedClientDto = ClientDto.builder()
                .id(1L)
                .name("Oleg")
                .surname("Vasya")
                .phoneNumber("125125")
                .passportId(expectedPassportID)
                .build();
        clientService.clear();

        Client save = clientService.save(expectedClientDto);

        expectedClientDto.setId(save.getId());

        Client actualClient = clientService.findByPassportId(expectedPassportID);

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }
}
