package service;

import dto.OrderDto;
import dto.VoyageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.VoyageServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

class VoyageServiceTest {
    private final VoyageService voyageService = new VoyageServiceImpl();

    @Test
    void addVoyage() throws SQLException {
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        VoyageDto voyage = new VoyageDto();
        voyage.setId(1L);
        voyage.setExpectedOrders(Arrays.asList(OrderDto.builder().build(), OrderDto.builder().build()));
        voyage.setDispatchedOrders(Arrays.asList(OrderDto.builder().build(), OrderDto.builder().build()));
        voyage.setSendingTime(sendingTime);
        voyage.setDeparturePoint("Minsk");
        String expected = "Moscow";
        voyage.setDestinationPoint(expected);

        VoyageDto addVoyage = voyageService.addVoyage(voyage);
        String actual = addVoyage.getDestinationPoint();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteVoyage() throws SQLException {
        VoyageDto firstVoyage = new VoyageDto();
        firstVoyage.setId(1L);
        VoyageDto secondVoyage = new VoyageDto();
        secondVoyage.setId(2L);
        VoyageDto thirdVoyage = new VoyageDto();
        thirdVoyage.setId(3L);

        voyageService.addVoyage(firstVoyage);
        voyageService.addVoyage(secondVoyage);
        voyageService.addVoyage(thirdVoyage);

        voyageService.deleteVoyage(firstVoyage.getId());

        int expected = 2;
        int actual = voyageService.findAllVoyages().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllVoyages() throws SQLException {
        VoyageDto firstVoyage = new VoyageDto();
        VoyageDto secondVoyage = new VoyageDto();
        VoyageDto thirdVoyage = new VoyageDto();

        voyageService.addVoyage(firstVoyage);
        voyageService.addVoyage(secondVoyage);
        voyageService.addVoyage(thirdVoyage);

        int expected = 3;

        int actual = voyageService.findAllVoyages().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getVoyage() throws SQLException {
        VoyageDto voyage = new VoyageDto();
        voyage.setId(1L);

        GregorianCalendar expectedTime = new GregorianCalendar();

        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);
        voyage.setSendingTime(expectedTime);

        voyageService.addVoyage(voyage);

        Calendar actualTime = voyageService.getVoyage(1).getSendingTime();

        Assertions.assertEquals(expectedTime, actualTime);
    }

    @Test
    void update() throws SQLException {
        VoyageDto expectedVoyage = new VoyageDto();
        expectedVoyage.setId(1L);
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        expectedVoyage.setSendingTime(sendingTime);

        voyageService.addVoyage(expectedVoyage);

        GregorianCalendar expectedTime = new GregorianCalendar();
        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        expectedVoyage.setSendingTime(expectedTime);

        VoyageDto actualVoyage = voyageService.update(expectedVoyage);

        Assertions.assertEquals(expectedVoyage, actualVoyage);
    }
}