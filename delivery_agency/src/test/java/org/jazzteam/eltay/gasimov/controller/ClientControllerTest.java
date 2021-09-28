package org.jazzteam.eltay.gasimov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
@Log
class ClientControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void addNewClient() throws Exception {
        ClientDto expected = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("1124")
                .passportId("1eqw12")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findById() throws Exception {
        ClientDto expected = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("1124")
                .passportId("1eqw12")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get(CLIENTS_URL + "/" + new Gson().fromJson(result.getResponse().getContentAsString(), ClientDto.class).getId()))
                                .andExpect(status().isOk())
                                .andReturn())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByPassportId() throws Exception {
        ClientDto expected = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("1124")
                .passportId("1eqw12")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get(CLIENTS_BY_PASSPORT_URL).param("passportId", new Gson().fromJson(result.getResponse().getContentAsString(), ClientDto.class).getPassportId()))
                                .andExpect(status().isOk())
                                .andReturn())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByPhoneNumber() throws Exception {
        ClientDto expected = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("1124")
                .passportId("1eqw12")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get(CLIENTS_BY_PHONE_NUMBER_URL).param("phoneNumber", new Gson().fromJson(result.getResponse().getContentAsString(), ClientDto.class).getPhoneNumber()))
                                .andExpect(status().isOk())
                                .andReturn())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getGeneratedCode() throws Exception {
        ClientDto expected = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("1124")
                .passportId("1eqw12")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get(GET_GENERATED_CODE_URL).param("phoneNumber", new Gson().fromJson(result.getResponse().getContentAsString(), ClientDto.class).getPhoneNumber()))
                                .andExpect(status().isOk())
                                .andReturn())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        expected.setId(actual.getId());
        assertNotNull(actual);
    }

    @Test
    void findAllClients() throws Exception {
        ClientDto firstClient = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("11244")
                .passportId("1eqw12214")
                .build();
        ClientDto secondClient = ClientDto.builder().name("Vasya")
                .surname("asfsf")
                .phoneNumber("11214")
                .passportId("1eqw12124")
                .build();

        mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(firstClient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(
                                post(CLIENTS_URL)
                                        .content(objectMapper.writeValueAsString(secondClient))
                                        .contentType(MediaType.APPLICATION_JSON)
                        ))
                .andReturn();
        MvcResult mvcResult = mockMvc.perform(
                        get(CLIENTS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Type itemsListType = new TypeToken<List<ClientDto>>() {
        }.getType();
        List<ClientDto> listItemsDes = new Gson().fromJson(responseBody, itemsListType);
        firstClient.setId(listItemsDes.get(0).getId());
        secondClient.setId(listItemsDes.get(1).getId());
        assertEquals(Arrays.asList(firstClient, secondClient), listItemsDes);
    }

    @Test
    void deleteClient() throws Exception {
        ClientDto firstClient = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("11244")
                .passportId("1eqw12214")
                .build();
        ClientDto secondClient = ClientDto.builder().name("Vasya")
                .surname("asfsf")
                .phoneNumber("11214")
                .passportId("1eqw12124")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(firstClient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(
                                post(CLIENTS_URL)
                                        .content(objectMapper.writeValueAsString(secondClient))
                                        .contentType(MediaType.APPLICATION_JSON)
                        ))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        mockMvc.perform(
                        delete(CLIENTS_URL + "/" + actual.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
        List<ClientDto> actualList = clientService.findAll().stream().map(client -> modelMapper.map(client, ClientDto.class)).collect(Collectors.toList());
        for (ClientDto clientDto : actualList) {
            if (clientDto.getPhoneNumber().equals(secondClient.getPhoneNumber())) {
                secondClient.setId(clientDto.getId());
            }
        }
        assertEquals(Collections.singletonList(secondClient), actualList);
    }

    @Test
    void updateClient() throws Exception {
        ClientDto firstClient = ClientDto.builder().name("Igor")
                .surname("FAWfa")
                .phoneNumber("11244")
                .passportId("1eqw12214")
                .build();
        mockMvc.perform(
                        post(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(firstClient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
        Client foundClient = clientService.findByPhoneNumber(firstClient.getPhoneNumber());
        foundClient.setName("qweqwe12eq");
        MvcResult mvcResult = mockMvc.perform(
                        put(CLIENTS_URL)
                                .content(objectMapper.writeValueAsString(foundClient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isResetContent())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ClientDto actual = new Gson().fromJson(responseBody, ClientDto.class);
        assertEquals(modelMapper.map(foundClient, ClientDto.class), actual);
    }
}