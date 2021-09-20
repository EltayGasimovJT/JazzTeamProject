package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.entity.OrderState;
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
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
class OrderStateServiceTest {
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private ModelMapper modelMapper;


    @Test
    void save() {
        OrderStateDto expected = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderState actual = orderStateService.save(expected);
        expected.setId(actual.getId());
        Assertions.assertEquals(expected, modelMapper.map(actual, OrderStateDto.class));
    }

    @Test
    void delete() {
        OrderStateDto firstState = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();
        OrderStateDto secondState = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderStateDto savedFirstState = modelMapper.map(orderStateService.save(firstState), OrderStateDto.class);
        OrderStateDto savedSecondState = modelMapper.map(orderStateService.save(secondState), OrderStateDto.class);

        orderStateService.delete(savedFirstState.getId());

        List<OrderStateDto> actual = orderStateService.findAll().stream()
                .map(orderState -> modelMapper.map(orderState, OrderStateDto.class))
                .collect(Collectors.toList());


        Assertions.assertEquals(Collections.singletonList(savedSecondState), actual);
    }

    @Test
    void findAll() {
        OrderStateDto firstState = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();
        OrderStateDto secondState = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderStateDto savedFirstState = modelMapper.map(orderStateService.save(firstState), OrderStateDto.class);
        OrderStateDto savedSecondState = modelMapper.map(orderStateService.save(secondState), OrderStateDto.class);

        List<OrderStateDto> actual = orderStateService.findAll().stream()
                .map(orderState -> modelMapper.map(orderState, OrderStateDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(savedFirstState,savedSecondState), actual);
    }

    @Test
    void findOne() {
        OrderStateDto expected = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderState foundState = orderStateService.save(expected);
        expected.setId(foundState.getId());
        OrderStateDto actual = modelMapper.map(orderStateService.findOne(foundState.getId()), OrderStateDto.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        OrderStateDto expected = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderState foundState = orderStateService.save(expected);
        foundState.setId(foundState.getId());
        foundState.setState("На пути");

        OrderStateDto actual = modelMapper.map(orderStateService.update(modelMapper.map(foundState, OrderStateDto.class)), OrderStateDto.class);
        String expectedState = "На пути";
        Assertions.assertEquals(expectedState, actual.getState());
    }

    @Test
    void findByState() {
        OrderStateDto expected = OrderStateDto
                .builder()
                .state("Готов")
                .prefix("Заказ ")
                .suffix(" к выдаче.")
                .build();

        OrderState foundState = orderStateService.save(expected);
        expected.setId(foundState.getId());
        OrderStateDto actual = modelMapper.map(orderStateService.findByState(foundState.getState()), OrderStateDto.class);
        Assertions.assertEquals(expected, actual);
    }
}