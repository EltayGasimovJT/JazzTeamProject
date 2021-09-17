/*
package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.Voyage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

@Sql(scripts = "/drop-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class VoyageServiceTest {
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    public static Stream<Arguments> ordersAndProcessingPointsForTest() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        processingPointToTest.setWarehouseId(1L);
        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .build();

        return Stream.of(
                Arguments.of(firstOrderToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void addVoyage(OrderDto orderToTest) throws ObjectNotFoundException {
        orderService.save(orderToTest);
        GregorianCalendar sentAt = new GregorianCalendar();
        sentAt.set(Calendar.HOUR_OF_DAY, 12);
        sentAt.set(Calendar.MINUTE, 30);

        VoyageDto voyageToTest = new VoyageDto();
        voyageToTest.setId(1L);
        voyageToTest.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        voyageToTest.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        voyageToTest.setDeparturePoint("Minsk");
        String expected = "Moscow";
        voyageToTest.setDestinationPoint(expected);

        Voyage savedVoyage = voyageService.save(voyageToTest);

        String actual = savedVoyage.getDestinationPoint();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void deleteVoyage(OrderDto orderToTest) {
        VoyageDto firstVoyageToTest = new VoyageDto();
        firstVoyageToTest.setId(1L);
        firstVoyageToTest.setDestinationPoint("Moskov");
        firstVoyageToTest.setDeparturePoint("Pskov");
        firstVoyageToTest.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        firstVoyageToTest.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        VoyageDto secondVoyageToTest = new VoyageDto();
        secondVoyageToTest.setId(2L);
        secondVoyageToTest.setDestinationPoint("Moskov");
        secondVoyageToTest.setDeparturePoint("Pskov");
        secondVoyageToTest.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        secondVoyageToTest.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        VoyageDto thirdVoyageToTest = new VoyageDto();
        thirdVoyageToTest.setId(3L);
        thirdVoyageToTest.setDestinationPoint("Moskov");
        thirdVoyageToTest.setDeparturePoint("Pskov");
        thirdVoyageToTest.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest));
        thirdVoyageToTest.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest));

        voyageService.save(firstVoyageToTest);
        voyageService.save(secondVoyageToTest);
        voyageService.save(thirdVoyageToTest);

        voyageService.delete(firstVoyageToTest.getId());

        int expected = 2;
        int actual = voyageService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void findAllVoyages(OrderDto orderToTest) {
        VoyageDto firstVoyage = new VoyageDto();
        firstVoyage.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));
        firstVoyage.setDestinationPoint("Moskov");
        firstVoyage.setDeparturePoint("Pskov");
        firstVoyage.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));
        VoyageDto secondVoyage = new VoyageDto();
        secondVoyage.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));
        secondVoyage.setDestinationPoint("Moskov");
        secondVoyage.setDeparturePoint("Pskov");
        secondVoyage.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));
        VoyageDto thirdVoyage = new VoyageDto();
        thirdVoyage.setDestinationPoint("Moskov");
        thirdVoyage.setDeparturePoint("Pskov");
        thirdVoyage.setDispatchedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));
        thirdVoyage.setExpectedOrders(Arrays.asList(
                orderToTest,
                orderToTest
        ));

        voyageService.save(firstVoyage);
        voyageService.save(secondVoyage);
        voyageService.save(thirdVoyage);

        int expected = 3;

        int actual = voyageService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void getVoyage(OrderDto orderDtoToTest) {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouseId(1L);
        VoyageDto voyageToTest = new VoyageDto();
        voyageToTest.setId(1L);
        voyageToTest.setDispatchedOrders(Arrays.asList(
                        new OrderDto(),
                        new OrderDto()
                )
        );
        voyageToTest.setExpectedOrders(Arrays.asList(
                        new OrderDto(),
                        new OrderDto()
                )
        );
        voyageToTest.setDestinationPoint("Moskov");
        voyageToTest.setDeparturePoint("Pskov");
        voyageToTest.setDestinationPoint("Moskov");
        voyageToTest.setDeparturePoint("Pskov");
        voyageToTest.setDispatchedOrders(Arrays.asList(
                        orderDtoToTest,
                        orderDtoToTest
                )
        );
        voyageToTest.setExpectedOrders(Arrays.asList(
                        orderDtoToTest,
                        orderDtoToTest
                )
        );
        GregorianCalendar expectedTime = new GregorianCalendar();

        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        Voyage expected = voyageService.save(voyageToTest);

        Voyage actual = voyageService.findOne(1);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void update(OrderDto orderToSave) {
        VoyageDto expectedVoyage = new VoyageDto();
        expectedVoyage.setId(1L);
        expectedVoyage.setDestinationPoint("Moskov");
        expectedVoyage.setDeparturePoint("Pskov");
        expectedVoyage.setDispatchedOrders(Arrays.asList(
                        orderToSave,
                        orderToSave
                )
        );
        expectedVoyage.setExpectedOrders(Arrays.asList(
                        orderToSave,
                        orderToSave
                )
        );
        GregorianCalendar sentAt = new GregorianCalendar();
        sentAt.set(Calendar.HOUR_OF_DAY, 12);
        sentAt.set(Calendar.MINUTE, 30);

        voyageService.save(expectedVoyage);

        GregorianCalendar expectedTime = new GregorianCalendar();
        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        Voyage actualVoyage = voyageService.update(expectedVoyage);

        VoyageDto actualDto = modelMapper.map(actualVoyage, VoyageDto.class);

        Assertions.assertEquals(expectedVoyage, actualDto);
    }
}
*/
