package service;

import dto.VoyageDto;
import entity.Order;
import entity.Voyage;
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

        VoyageDto voyage = VoyageDto.builder().build();
        voyage.setId(1L);
        voyage.setExpectedOrders(Arrays.asList(Order.builder().build(), Order.builder().build()));
        voyage.setDispatchedOrders(Arrays.asList(Order.builder().build(), Order.builder().build()));
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
        VoyageDto firstVoyage = VoyageDto.builder().build();
        firstVoyage.setId(1L);
        VoyageDto secondVoyage = VoyageDto.builder().build();
        secondVoyage.setId(2L);
        VoyageDto thirdVoyage = VoyageDto.builder().build();
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
        VoyageDto firstVoyage = VoyageDto.builder().build();
        VoyageDto secondVoyage = VoyageDto.builder().build();
        VoyageDto thirdVoyage = VoyageDto.builder().build();

        voyageService.addVoyage(firstVoyage);
        voyageService.addVoyage(secondVoyage);
        voyageService.addVoyage(thirdVoyage);

        int expected = 3;

        int actual = voyageService.findAllVoyages().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getVoyage() throws SQLException {
        VoyageDto voyage = VoyageDto.builder().build();
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
        VoyageDto voyage = VoyageDto.builder().build();
        voyage.setId(1L);
        GregorianCalendar sendingTime = new GregorianCalendar();
        sendingTime.set(Calendar.HOUR_OF_DAY, 12);
        sendingTime.set(Calendar.MINUTE, 30);

        voyage.setSendingTime(sendingTime);

        voyageService.addVoyage(voyage);

        GregorianCalendar expectedTime = new GregorianCalendar();
        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        voyage.setSendingTime(expectedTime);

        Calendar actualTime = voyageService.update(voyage).getSendingTime();

        Assertions.assertEquals(expectedTime, actualTime);
    }
}