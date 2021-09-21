package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ClientServiceTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;

    private static Stream<Arguments> testClients() {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("Igor")
                .surname("igor")
                .phoneNumber("1")
                .passportId("1")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("Alex")
                .surname("igor")
                .phoneNumber("2")
                .passportId("2")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("Eltay")
                .surname("igor")
                .phoneNumber("3")
                .passportId("3")
                .build();

        return Stream.of(
                Arguments.of(firstClientToTest, "1"),
                Arguments.of(secondClientToTest, "2"),
                Arguments.of(thirdClientToTest, "3")
        );
    }

    @ParameterizedTest
    @MethodSource("testClients")
    void testAddClient(ClientDto expectedClientDto, String expectedPassportId) throws ObjectNotFoundException {
        Client savedClient = clientService.save(expectedClientDto);
        Client actualClient = clientService.findClientByPassportId(expectedPassportId);
        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);
        ClientDto savedClientDto = modelMapper.map(savedClient, ClientDto.class);
        Assertions.assertEquals(savedClientDto, actualClientDto);
    }

    @Test
    void deleteClient() throws ObjectNotFoundException {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("firstClient")
                .surname("igor")
                .phoneNumber("4")
                .passportId("4")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("secondClient")
                .surname("igor")
                .phoneNumber("5")
                .passportId("5")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("thirdClient")
                .surname("igor")
                .phoneNumber("6")
                .passportId("6")
                .build();


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
    void findAllClients() throws ObjectNotFoundException {
        ClientDto firstClientToTest = ClientDto.builder()
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("7")
                .passportId("7")
                .build();
        ClientDto secondClientToTest = ClientDto.builder()
                .name("secondClient")
                .surname("Gleb")
                .phoneNumber("8")
                .passportId("8")
                .build();
        ClientDto thirdClientToTest = ClientDto.builder()
                .name("thirdClient")
                .surname("igor")
                .phoneNumber("9")
                .passportId("9")
                .build();


        firstClientToTest.setId(clientService.save(firstClientToTest).getId());
        secondClientToTest.setId(clientService.save(secondClientToTest).getId());
        thirdClientToTest.setId(clientService.save(thirdClientToTest).getId());

        List<Client> actualClients = clientService.findAll();

        List<ClientDto> actualClientDtos = actualClients.stream()
                .map(actualClientDto -> modelMapper.map(actualClientDto, ClientDto.class))
                .collect(Collectors.toList());
        final int expectedCount = 3;
        Assertions.assertEquals(expectedCount, actualClientDtos.size());
    }

    @Test
    void findById() throws ObjectNotFoundException {
        ClientDto expectedClientDto = ClientDto.builder()
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("10")
                .passportId("10")
                .build();

        Client savedClient = clientService.save(expectedClientDto);
        expectedClientDto.setId(savedClient.getId());
        Client actualClient = clientService.findById(savedClient.getId());
        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void findByPhoneNumber() throws ObjectNotFoundException {
        ClientDto expectedClientDto = ClientDto.builder()
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("10")
                .passportId("10")
                .build();

        Client savedClient = clientService.save(expectedClientDto);
        expectedClientDto.setId(savedClient.getId());
        Client actualClient = clientService.findByPhoneNumber(savedClient.getPhoneNumber());

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void generateCodeForClient() throws ObjectNotFoundException {
        ClientDto expectedClientDto = ClientDto.builder()
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("10")
                .passportId("10")
                .build();

        Client savedClient = clientService.save(expectedClientDto);
        expectedClientDto.setId(savedClient.getId());
        Client actualClient = clientService.generateCodeForClient(savedClient.getPhoneNumber());
        Assertions.assertNotNull(actualClient.getCode().getGeneratedCode());
    }

    @Test
    void update() throws ObjectNotFoundException {
        ClientDto expectedClientDto = ClientDto.builder()
                .name("Oleg")
                .surname("Vasya")
                .phoneNumber("11")
                .passportId("11")
                .build();

        Client savedClient = clientService.save(expectedClientDto);

        expectedClientDto.setId(savedClient.getId());
        expectedClientDto.setName("Igor");

        clientService.update(expectedClientDto);

        Client actualClient = clientService.findById(savedClient.getId());

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    void findByPassportId() throws ObjectNotFoundException {
        String expectedPassportID = "12";
        ClientDto expectedClientDto = ClientDto.builder()
                .name("Oleg")
                .surname("Vasya")
                .phoneNumber("12")
                .passportId(expectedPassportID)
                .build();

        Client save = clientService.save(expectedClientDto);

        expectedClientDto.setId(save.getId());

        Client actualClient = clientService.findClientByPassportId(expectedPassportID);

        ClientDto actualClientDto = modelMapper.map(actualClient, ClientDto.class);

        Assertions.assertEquals(expectedClientDto, actualClientDto);
    }
}

