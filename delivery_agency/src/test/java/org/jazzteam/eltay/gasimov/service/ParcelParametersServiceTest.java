package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.ParcelParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ParcelParametersServiceTest {
    @Autowired
    private ParcelParametersService parcelParametersService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void findById() {
        ParcelParametersDto expected = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParameters save = parcelParametersService.save(expected);
        ParcelParametersDto savedParcelParameters = modelMapper.map(save, ParcelParametersDto.class);
        expected.setId(savedParcelParameters.getId());
        ParcelParameters byId = parcelParametersService.findById(savedParcelParameters.getId());
        ParcelParametersDto actual = modelMapper.map(byId, ParcelParametersDto.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        ParcelParametersDto first = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParametersDto second = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParametersDto firstSaved = modelMapper.map(parcelParametersService.save(first), ParcelParametersDto.class);
        ParcelParametersDto secondSaved = modelMapper.map(parcelParametersService.save(second), ParcelParametersDto.class);
        List<ParcelParametersDto> actual = parcelParametersService.findAll().stream()
                .map(parcelParameters -> modelMapper.map(parcelParameters, ParcelParametersDto.class))
                .collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(firstSaved, secondSaved), actual);
    }

    @Test
    void delete() {
        ParcelParametersDto first = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParametersDto second = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParametersDto firstSaved = modelMapper.map(parcelParametersService.save(first), ParcelParametersDto.class);
        ParcelParametersDto secondSaved = modelMapper.map(parcelParametersService.save(second), ParcelParametersDto.class);
        parcelParametersService.delete(secondSaved.getId());
        List<ParcelParametersDto> actual = parcelParametersService.findAll().stream()
                .map(parcelParameters -> modelMapper.map(parcelParameters, ParcelParametersDto.class))
                .collect(Collectors.toList());
        Assertions.assertEquals(Collections.singletonList(firstSaved), actual);
    }

    @Test
    void save() {
        ParcelParametersDto expected = ParcelParametersDto
                .builder()
                .height(50.0)
                .weight(50.0)
                .width(50.0)
                .length(50.0)
                .build();
        ParcelParametersDto actual = modelMapper.map(parcelParametersService.save(expected), ParcelParametersDto.class);
        expected.setId(actual.getId());
        Assertions.assertEquals(expected, actual);
    }
}