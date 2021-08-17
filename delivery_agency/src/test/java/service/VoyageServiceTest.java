package service;

import dto.*;
import entity.Voyage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        OrderProcessingPointDto processingPoint = new OrderProcessingPointDto();
        processingPoint.setLocation("Russia");
        processingPoint.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToAdd = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(processingPoint)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();

        return Stream.of(
                Arguments.of(firstOrderToAdd)
        );
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void addVoyage(OrderDto orderToSave) throws SQLException {
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        VoyageDto voyage = new VoyageDto();
        voyage.setId(1L);
        voyage.setSendingTime(sendingTime);
        voyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        voyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        voyage.setDeparturePoint("Minsk");
        String expected = "Moscow";
        voyage.setDestinationPoint(expected);

        Voyage addVoyage = voyageService.save(voyage);

        String actual = addVoyage.getDestinationPoint();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void deleteVoyage(OrderDto orderToSave) throws SQLException {
        VoyageDto firstVoyage = new VoyageDto();
        firstVoyage.setId(1L);
        firstVoyage.setDestinationPoint("Moskov");
        firstVoyage.setDeparturePoint("Pskov");
        firstVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        firstVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        VoyageDto secondVoyage = new VoyageDto();
        secondVoyage.setId(2L);
        secondVoyage.setDestinationPoint("Moskov");
        secondVoyage.setDeparturePoint("Pskov");
        secondVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        secondVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        VoyageDto thirdVoyage = new VoyageDto();
        thirdVoyage.setId(3L);
        thirdVoyage.setDestinationPoint("Moskov");
        thirdVoyage.setDeparturePoint("Pskov");
        thirdVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave));
        thirdVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave));

        voyageService.save(firstVoyage);
        voyageService.save(secondVoyage);
        voyageService.save(thirdVoyage);

        voyageService.delete(firstVoyage.getId());

        int expected = 2;
        int actual = voyageService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointsForTest")
    void findAllVoyages(OrderDto orderToSave) throws SQLException {
        VoyageDto firstVoyage = new VoyageDto();
        firstVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave
        ));
        firstVoyage.setDestinationPoint("Moskov");
        firstVoyage.setDeparturePoint("Pskov");
        firstVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave
        ));
        VoyageDto secondVoyage = new VoyageDto();
        secondVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave
        ));
        secondVoyage.setDestinationPoint("Moskov");
        secondVoyage.setDeparturePoint("Pskov");
        secondVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave
        ));
        VoyageDto thirdVoyage = new VoyageDto();
        thirdVoyage.setDestinationPoint("Moskov");
        thirdVoyage.setDeparturePoint("Pskov");
        thirdVoyage.setDispatchedOrders(Arrays.asList(
                orderToSave,
                orderToSave
        ));
        thirdVoyage.setExpectedOrders(Arrays.asList(
                orderToSave,
                orderToSave
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
    void getVoyage(OrderDto orderDtoToSave) throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        VoyageDto voyage = new VoyageDto();
        voyage.setId(1L);
        voyage.setDispatchedOrders(Arrays.asList(
                        new OrderDto(),
                        new OrderDto()
                )
        );
        voyage.setExpectedOrders(Arrays.asList(
                        new OrderDto(),
                        new OrderDto()
                )
        );
        voyage.setDestinationPoint("Moskov");
        voyage.setDeparturePoint("Pskov");
        voyage.setDestinationPoint("Moskov");
        voyage.setDeparturePoint("Pskov");
        voyage.setDispatchedOrders(Arrays.asList(
                        orderDtoToSave,
                        orderDtoToSave
                )
        );
        voyage.setExpectedOrders(Arrays.asList(
                        orderDtoToSave,
                        orderDtoToSave
                )
        );
        GregorianCalendar expectedTime = new GregorianCalendar();

        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);
        voyage.setSendingTime(expectedTime);

        voyageService.save(voyage);

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
