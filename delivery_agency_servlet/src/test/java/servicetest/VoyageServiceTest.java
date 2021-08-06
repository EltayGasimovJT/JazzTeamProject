package servicetest;

import entity.Order;
import entity.Voyage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import servicetest.impl.VoyageServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;

class VoyageServiceTest {
    private final VoyageService voyageService = new VoyageServiceImpl();

    @Test
    void addVoyage() throws SQLException {
        Voyage voyage = new Voyage();
        voyage.setId(1L);
        voyage.setExpectedOrders(Arrays.asList(Order.builder().build(), Order.builder().build()));
        voyage.setDispatchedOrders(Arrays.asList(Order.builder().build(), Order.builder().build()));
        voyage.setSendingTime("12:30");
        voyage.setDeparturePoint("Minsk");
        voyage.setDestinationPoint("Moscow");

        Voyage addVoyage = voyageService.addVoyage(voyage);
        Assert.assertEquals("Moscow", addVoyage.getDestinationPoint());
    }

    @Test
    void deleteVoyage() throws SQLException {
        Voyage firstVoyage = new Voyage();
        firstVoyage.setId(1L);
        Voyage secondVoyage = new Voyage();
        secondVoyage.setId(2L);
        Voyage thirdVoyage = new Voyage();
        thirdVoyage.setId(3L);

        voyageService.addVoyage(firstVoyage);
        voyageService.addVoyage(secondVoyage);
        voyageService.addVoyage(thirdVoyage);

        voyageService.deleteVoyage(firstVoyage);

        Assert.assertEquals(2, voyageService.findAllVoyages().size());
    }

    @Test
    void findAllVoyages() throws SQLException {
        Voyage firstVoyage = new Voyage();
        Voyage secondVoyage = new Voyage();
        Voyage thirdVoyage = new Voyage();

        voyageService.addVoyage(firstVoyage);
        voyageService.addVoyage(secondVoyage);
        voyageService.addVoyage(thirdVoyage);

        Assert.assertEquals(3, voyageService.findAllVoyages().size());
    }

    @Test
    void getVoyage() throws SQLException {
        Voyage voyage = new Voyage();
        voyage.setId(1L);
        voyage.setSendingTime("12:30");

        voyageService.addVoyage(voyage);

        Voyage getVoyageByID = voyageService.getVoyage(1);

        Assert.assertEquals("12:30", getVoyageByID.getSendingTime());
    }

    @Test
    void update() throws SQLException {
        Voyage voyage = new Voyage();
        voyage.setId(1L);
        voyage.setSendingTime("12:30");

        voyageService.addVoyage(voyage);

        voyage.setSendingTime("15:30");

        Voyage update = voyageService.update(voyage);

        String expectedTime = "15:30";

        Assert.assertEquals(expectedTime, update.getSendingTime());
    }
}