package service;

import dto.*;
import entity.Voyage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.VoyageServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

class VoyageServiceTest {
    private final VoyageService voyageService = new VoyageServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    public static Stream<Arguments> ordersAndProcessingPointsForTest() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        processingPointToTest.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(processingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();

        return Stream.of(
                Arguments.of(firstOrderToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void addVoyage(OrderDto orderToTest) throws SQLException {
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        VoyageDto voyageToTest = new VoyageDto();
        voyageToTest.setId(1L);
        voyageToTest.setSendingTime(sendingTime);
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
    void deleteVoyage(OrderDto orderToTest) throws SQLException {
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
    void findAllVoyages(OrderDto orderToTest) throws SQLException {
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
    void getVoyage(OrderDto orderDtoToTest) throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(new WarehouseDto());
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
        voyageToTest.setSendingTime(expectedTime);

        voyageService.save(voyageToTest);

        Calendar actualTime = voyageService.findOne(1).getSendingTime();

        Assertions.assertEquals(expectedTime, actualTime);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void update(OrderDto orderToSave) throws SQLException {
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
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        expectedVoyage.setSendingTime(sendingTime);

        voyageService.save(expectedVoyage);

        GregorianCalendar expectedTime = new GregorianCalendar();
        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        expectedVoyage.setSendingTime(expectedTime);

        Voyage actualVoyage = voyageService.update(expectedVoyage);

        VoyageDto actualDto = modelMapper.map(actualVoyage, VoyageDto.class);

        Assertions.assertEquals(expectedVoyage, actualDto);
    }
}