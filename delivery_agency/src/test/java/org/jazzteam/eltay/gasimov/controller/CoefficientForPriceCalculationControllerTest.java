package org.jazzteam.eltay.gasimov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.COEFFICIENTS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
@Log
class CoefficientForPriceCalculationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Russia");
        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(132.0)
                                .width(132.0)
                                .length(133.0)
                                .weight(2032.0).build()
                )
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto firstCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        destinationPlaceToTest.setLocation("Poland");
        destinationPlaceToTest.setId(2L);
        OrderDto secondOrder = OrderDto.builder()
                .id(2L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(432.0)
                                .width(130.0)
                                .length(211.0)
                                .weight(20.0).build()
                )
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto secondCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        destinationPlaceToTest.setLocation("Ukraine");
        OrderDto thirdOrder = OrderDto.builder()
                .id(3L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(424.0)
                                .width(334.0)
                                .length(130.0)
                                .weight(340.0).build())
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto thirdCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3L)
                .build();

        return Stream.of(
                Arguments.of(firstOrder, firstCoefficientToTest, BigDecimal.valueOf(7.4462)),
                Arguments.of(secondOrder, secondCoefficientToTest, BigDecimal.valueOf(6.6011)),
                Arguments.of(thirdOrder, thirdCoefficientToTest, BigDecimal.valueOf(6.5218))
        );
    }

    @Test
    void save() throws Exception {
        CoefficientForPriceCalculationDto expected = CoefficientForPriceCalculationDto.builder()
                .country("Belarus")
                .parcelSizeLimit(50)
                .countryCoefficient(1.2)
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto actual = new Gson().fromJson(responseBody, CoefficientForPriceCalculationDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findById() throws Exception {
        CoefficientForPriceCalculationDto expected = CoefficientForPriceCalculationDto.builder()
                .country("Belarus")
                .parcelSizeLimit(50)
                .countryCoefficient(1.2)
                .build();
        MvcResult mvcResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String responseOnSave = mvcResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto savedCoefficient = new Gson().fromJson(responseOnSave, CoefficientForPriceCalculationDto.class);
        expected.setId(savedCoefficient.getId());
        MvcResult findByIdResult = mockMvc.perform(
                        get(COEFFICIENTS_URL + "/" + expected.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        String responseOnFind = findByIdResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto actual = new Gson().fromJson(responseOnFind, CoefficientForPriceCalculationDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void findAll() throws Exception {
        CoefficientForPriceCalculationDto firstCoefficient = CoefficientForPriceCalculationDto.builder()
                .country("Russia")
                .parcelSizeLimit(60)
                .countryCoefficient(1.8)
                .build();
        CoefficientForPriceCalculationDto secondCoefficient = CoefficientForPriceCalculationDto.builder()
                .country("Belarus")
                .parcelSizeLimit(50)
                .countryCoefficient(1.2)
                .build();
        MvcResult firstCoefficientResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(firstCoefficient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String firstCoefficientResponse = firstCoefficientResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto firstSaved = new Gson().fromJson(firstCoefficientResponse, CoefficientForPriceCalculationDto.class);
        firstCoefficient.setId(firstSaved.getId());
        MvcResult secondCoefficientResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(secondCoefficient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String secondCoefficientResponse = secondCoefficientResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto secondSaved = new Gson().fromJson(secondCoefficientResponse, CoefficientForPriceCalculationDto.class);
        secondCoefficient.setId(secondSaved.getId());
        MvcResult findAllCoefficientsResult = mockMvc.perform(
                        get(COEFFICIENTS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        String responseFindAll = findAllCoefficientsResult.getResponse().getContentAsString();
        Type itemsListType = new TypeToken<List<CoefficientForPriceCalculationDto>>() {
        }.getType();
        List<CoefficientForPriceCalculationDto> actual = new Gson().fromJson(responseFindAll, itemsListType);

        assertEquals(Arrays.asList(firstCoefficient, secondCoefficient), actual);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto testOrder, CoefficientForPriceCalculationDto coefficient, BigDecimal expected) throws Exception {
        calculationService.save(coefficient);
        MvcResult calculatePriceResult = mockMvc.perform(
                        post("/calculatePrice/" + coefficient.getCountry())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testOrder.getParcelParameters()))
                )
                .andExpect(status().isOk())
                .andReturn();
        String resultPrice = calculatePriceResult.getResponse().getContentAsString();
        BigDecimal actual = BigDecimal.valueOf(Double.parseDouble(resultPrice));
        assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001);
    }

    @Test
    void deleteCoefficient() throws Exception {
        CoefficientForPriceCalculationDto firstCoefficient = CoefficientForPriceCalculationDto.builder()
                .country("Russia")
                .parcelSizeLimit(60)
                .countryCoefficient(1.8)
                .build();
        CoefficientForPriceCalculationDto secondCoefficient = CoefficientForPriceCalculationDto.builder()
                .country("Belarus")
                .parcelSizeLimit(50)
                .countryCoefficient(1.2)
                .build();
        MvcResult firstCoefficientResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(firstCoefficient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String firstCoefficientResponse = firstCoefficientResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto firstSaved = new Gson().fromJson(firstCoefficientResponse, CoefficientForPriceCalculationDto.class);
        firstCoefficient.setId(firstSaved.getId());
        MvcResult secondCoefficientResult = mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(secondCoefficient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String secondCoefficientResponse = secondCoefficientResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto secondSaved = new Gson().fromJson(secondCoefficientResponse, CoefficientForPriceCalculationDto.class);
        secondCoefficient.setId(secondSaved.getId());
        mockMvc.perform(
                        delete(COEFFICIENTS_URL + "/" + secondSaved.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
        List<CoefficientForPriceCalculationDto> actual = calculationService.findAll()
                .stream()
                .map(coefficient -> modelMapper.map(coefficient, CoefficientForPriceCalculationDto.class))
                .collect(Collectors.toList());

        assertEquals(Collections.singletonList(firstCoefficient), actual);
    }

    @Test
    void updateCoefficient() throws Exception {
        CoefficientForPriceCalculationDto expected = CoefficientForPriceCalculationDto.builder()
                .country("Belarus")
                .parcelSizeLimit(50)
                .countryCoefficient(1.2)
                .build();
        mockMvc.perform(
                        post(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        CoefficientForPriceCalculation savedCoefficient = calculationService.findByCountry(expected.getCountry());
        expected.setId(savedCoefficient.getId());
        savedCoefficient.setCountry("Russia");
        expected.setCountry("Russia");
        MvcResult findByIdResult = mockMvc.perform(
                        put(COEFFICIENTS_URL)
                                .content(objectMapper.writeValueAsString(savedCoefficient))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isResetContent())
                .andReturn();
        String responseOnFind = findByIdResult.getResponse().getContentAsString();
        CoefficientForPriceCalculationDto actual = new Gson().fromJson(responseOnFind, CoefficientForPriceCalculationDto.class);
        assertEquals(expected, actual);
    }
}