package org.jazzteam.eltay.gasimov.service;

import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
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
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
class OrderProcessingPointServiceTest {
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private ModelMapper modelMapper;


    private static Stream<Arguments> testOrderProcessingPoints() {
        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Minsk");
        firstProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPointToTest = new OrderProcessingPointDto();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Moscow");
        secondProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto thirdProcessingPointToTest = new OrderProcessingPointDto();
        thirdProcessingPointToTest.setId(3L);
        thirdProcessingPointToTest.setLocation("Grodno");
        thirdProcessingPointToTest.setWarehouse(new WarehouseDto());

        return Stream.of(
                Arguments.of(firstProcessingPointToTest, "Minsk"),
                Arguments.of(secondProcessingPointToTest, "Moscow"),
                Arguments.of(thirdProcessingPointToTest, "Grodno")
        );
    }

    @ParameterizedTest
    @MethodSource("testOrderProcessingPoints")
    void addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPoint, String expectedLocation) throws SQLException {
        orderProcessingPointService.clear();
        orderProcessingPointService.save(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.findOne(orderProcessingPoint.getId()).getLocation();
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() throws SQLException {
        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Polotsk");
        firstProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPointTest = new OrderProcessingPointDto();
        secondProcessingPointTest.setId(2L);
        secondProcessingPointTest.setLocation("Minsk");
        secondProcessingPointTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto thirdProcessingPointTest = new OrderProcessingPointDto();
        thirdProcessingPointTest.setId(3L);
        thirdProcessingPointTest.setLocation("Minsk");
        thirdProcessingPointTest.setWarehouse(new WarehouseDto());
        orderProcessingPointService.clear();

        orderProcessingPointService.save(firstProcessingPointToTest);
        orderProcessingPointService.save(secondProcessingPointTest);
        orderProcessingPointService.save(thirdProcessingPointTest);

        orderProcessingPointService.delete(firstProcessingPointToTest.getId());

        List<OrderProcessingPoint> actualProcessingPoints = orderProcessingPointService.findAll();

        List<OrderProcessingPointDto> actualProcessingPointDtos = new ArrayList<>();

        for (OrderProcessingPoint actualProcessingPoint : actualProcessingPoints) {
            WarehouseDto actualWarehouse = modelMapper.map(actualProcessingPoint.getWarehouse(), WarehouseDto.class);
            OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actualProcessingPoint, OrderProcessingPointDto.class);
            actualProcessingPointDto.setWarehouse(actualWarehouse);
            actualProcessingPointDtos.add(actualProcessingPointDto);
        }

        Assertions.assertEquals(Arrays.asList(secondProcessingPointTest, thirdProcessingPointTest)
                , actualProcessingPointDtos);
    }

    @Test
    void findAllOrderProcessingPoints() throws SQLException {
        OrderProcessingPointDto firstProcessingPointTest = new OrderProcessingPointDto();
        firstProcessingPointTest.setWarehouse(new WarehouseDto());
        firstProcessingPointTest.setLocation("Minsk");
        OrderProcessingPointDto secondProcessingPointTest = new OrderProcessingPointDto();
        secondProcessingPointTest.setWarehouse(new WarehouseDto());
        secondProcessingPointTest.setLocation("Gomel");
        OrderProcessingPointDto thirdProcessingPointTest = new OrderProcessingPointDto();
        thirdProcessingPointTest.setWarehouse(new WarehouseDto());
        thirdProcessingPointTest.setLocation("Polotsk");
        orderProcessingPointService.clear();

        orderProcessingPointService.save(firstProcessingPointTest);
        orderProcessingPointService.save(secondProcessingPointTest);
        orderProcessingPointService.save(thirdProcessingPointTest);

        List<OrderProcessingPoint> actualOrderProcessingPoints = orderProcessingPointService.findAll();

        List<OrderProcessingPointDto> actualProcessingPointDtos = new ArrayList<>();

        for (OrderProcessingPoint actualOrderProcessingPoint : actualOrderProcessingPoints) {
            WarehouseDto actualWarehouse = modelMapper.map(actualOrderProcessingPoint.getWarehouse(), WarehouseDto.class);
            OrderProcessingPointDto actualProcessingOrder = modelMapper.map(actualOrderProcessingPoint, OrderProcessingPointDto.class);
            actualProcessingOrder.setWarehouse(actualWarehouse);
            actualProcessingPointDtos.add(actualProcessingOrder);
        }

        Assertions.assertEquals(Arrays.asList(firstProcessingPointTest, secondProcessingPointTest, thirdProcessingPointTest)
                , actualProcessingPointDtos);
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setId(1L);
        processingPointToTest.setLocation("Minsk");
        processingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setId(2L);
        expectedProcessingPointDto.setLocation("Moscow");
        expectedProcessingPointDto.setWarehouse(new WarehouseDto());
        orderProcessingPointService.clear();

        orderProcessingPointService.save(processingPointToTest);
        orderProcessingPointService.save(expectedProcessingPointDto);

        OrderProcessingPoint actual = orderProcessingPointService.findOne(2);

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouse(modelMapper.map(actual.getWarehouse(), WarehouseDto.class));

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setId(1L);
        expectedProcessingPointDto.setLocation("Minsk");
        expectedProcessingPointDto.setWarehouse(new WarehouseDto());
        expectedProcessingPointDto.setExpectedOrders(new ArrayList<>());
        expectedProcessingPointDto.setDispatchedOrders(new ArrayList<>());
        orderProcessingPointService.clear();

        orderProcessingPointService.save(expectedProcessingPointDto);

        String newLocation = "Homel";

        expectedProcessingPointDto.setLocation(newLocation);

        OrderProcessingPoint actual = orderProcessingPointService.update(expectedProcessingPointDto);

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouse(modelMapper.map(actual.getWarehouse(), WarehouseDto.class));

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }
}
