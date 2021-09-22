package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.ClientsCodeDto;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CodeServiceTest {
    @Autowired
    private CodeService codeService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ClientService clientService;

    @Test
    void findById() {
        ClientsCodeDto clientsCodeDto = ClientsCodeDto.
                builder()
                .generatedCode("12412qw")
                .build();
        ClientsCodeDto expected = modelMapper.map(codeService.save(clientsCodeDto), ClientsCodeDto.class);
        ClientsCodeDto actual = modelMapper.map(codeService.findById(expected.getId()), ClientsCodeDto.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByClientId() throws ObjectNotFoundException {
        ClientDto expectedClientDto = ClientDto.builder()
                .name("firstClient")
                .surname("Vasya")
                .phoneNumber("10")
                .passportId("10")
                .build();

        ClientDto savedClient = modelMapper.map(clientService.save(expectedClientDto), ClientDto.class);
        expectedClientDto.setId(savedClient.getId());
        ClientsCodeDto clientsCodeDto = ClientsCodeDto.builder()
                .generatedCode("12412qw")
                .client(savedClient)
                .build();
        ClientsCodeDto expected = modelMapper.map(codeService.save(clientsCodeDto), ClientsCodeDto.class);
        ClientsCodeDto actual = modelMapper.map(codeService.findByClient(expectedClientDto), ClientsCodeDto.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        ClientsCodeDto firstCode = ClientsCodeDto.builder()
                .generatedCode("12412qw")
                .build();
        ClientsCodeDto secondCode = ClientsCodeDto.builder()
                .generatedCode("12412qw")
                .build();
        ClientsCode firstSavedCode = codeService.save(firstCode);
        ClientsCode secondSavedCode = codeService.save(secondCode);
        firstCode.setId(firstSavedCode.getId());
        secondCode.setId(secondSavedCode.getId());
        List<ClientsCodeDto> actual = codeService.findAll().stream().map(clientsCode -> modelMapper.map(clientsCode, ClientsCodeDto.class)).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(firstCode, secondCode), actual);
    }

    @Test
    void delete() {
        ClientsCodeDto firstCode = ClientsCodeDto.
                builder()
                .generatedCode("12412qw")
                .build();
        ClientsCodeDto secondCode = ClientsCodeDto.
                builder()
                .generatedCode("124122qw")
                .build();
        ClientsCode savedFirstCode = codeService.save(firstCode);
        codeService.save(secondCode);
        firstCode.setId(savedFirstCode.getId());
        codeService.delete(secondCode.getGeneratedCode());
        List<ClientsCodeDto> actual = codeService.findAll().stream().map(clientsCode -> modelMapper.map(clientsCode, ClientsCodeDto.class)).collect(Collectors.toList());
        Assertions.assertEquals(Collections.singletonList(firstCode), actual);
    }

    @Test
    void save() {
        ClientsCodeDto expected = ClientsCodeDto.
                builder()
                .generatedCode("12412qw")
                .build();
        ClientsCodeDto actual = modelMapper.map(codeService.save(expected), ClientsCodeDto.class);
        expected.setId(actual.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByCode() {
        ClientsCodeDto clientsCodeDto = ClientsCodeDto.
                builder()
                .generatedCode("12412qw")
                .build();
        ClientsCodeDto expected = modelMapper.map(codeService.save(clientsCodeDto), ClientsCodeDto.class);
        ClientsCodeDto actual = modelMapper.map(codeService.findByCode(clientsCodeDto.getGeneratedCode()), ClientsCodeDto.class);
        Assertions.assertEquals(expected, actual);
    }
}