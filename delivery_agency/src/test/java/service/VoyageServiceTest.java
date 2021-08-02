package service;

import entity.Order;
import entity.Voyage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.VoyageServiceImpl;

import java.util.Arrays;

class VoyageServiceTest {
    private final VoyageService voyageService = new VoyageServiceImpl();

    @Test
    void addVoyage() {
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
    void deleteVoyage() {
        Voyage voyage1 = new Voyage();
        voyage1.setId(1L);
        Voyage voyage2 = new Voyage();
        voyage2.setId(2L);
        Voyage voyage3 = new Voyage();
        voyage3.setId(3L);

        voyageService.addVoyage(voyage1);
        voyageService.addVoyage(voyage2);
        voyageService.addVoyage(voyage3);

        voyageService.deleteVoyage(voyage1);

        Assert.assertEquals(2, voyageService.findAllVoyages().size());
    }

    @Test
    void findAllVoyages() {
        Voyage voyage1 = new Voyage();
        Voyage voyage2 = new Voyage();
        Voyage voyage3 = new Voyage();

        voyageService.addVoyage(voyage1);
        voyageService.addVoyage(voyage2);
        voyageService.addVoyage(voyage3);

        Assert.assertEquals(3, voyageService.findAllVoyages().size());
    }

    @Test
    void getVoyage() {
        Voyage voyage1 = new Voyage();
        voyage1.setId(1L);
        voyage1.setSendingTime("12:30");

        voyageService.addVoyage(voyage1);

        Voyage voyage = voyageService.getVoyage(1);

        Assert.assertEquals("12:30", voyage.getSendingTime());
    }

    @Test
    void update() {
        Voyage voyage1 = new Voyage();
        voyage1.setId(1L);
        voyage1.setSendingTime("12:30");

        voyageService.addVoyage(voyage1);

        voyage1.setSendingTime("15:30");

        Voyage update = voyageService.update(voyage1);

        Assert.assertEquals("15:30", update.getSendingTime());
    }
}