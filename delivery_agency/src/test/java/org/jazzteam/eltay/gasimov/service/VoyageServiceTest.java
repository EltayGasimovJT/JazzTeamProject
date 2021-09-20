package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.VoyageDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Voyage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VoyageServiceTest {
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void addVoyage() {
        VoyageDto voyageToTest = new VoyageDto();
        voyageToTest.setDeparturePoint("Minsk");
        String expected = "Moscow";
        voyageToTest.setDestinationPoint(expected);

        Voyage savedVoyage = voyageService.save(voyageToTest);

        String actual = savedVoyage.getDestinationPoint();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteVoyage() {
        VoyageDto firstVoyageToTest = new VoyageDto();
        firstVoyageToTest.setDestinationPoint("Moskov");
        firstVoyageToTest.setDeparturePoint("Pskov");
        VoyageDto secondVoyageToTest = new VoyageDto();
        secondVoyageToTest.setDestinationPoint("Moskov");
        secondVoyageToTest.setDeparturePoint("Pskov");
        VoyageDto thirdVoyageToTest = new VoyageDto();
        thirdVoyageToTest.setDestinationPoint("Moskov");
        thirdVoyageToTest.setDeparturePoint("Pskov");

        Voyage firstToDelete = voyageService.save(firstVoyageToTest);
        voyageService.save(secondVoyageToTest);
        voyageService.save(thirdVoyageToTest);

        voyageService.delete(firstToDelete.getId());

        int expected = 2;
        int actual = voyageService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllVoyages() {
        VoyageDto firstVoyage = new VoyageDto();
        firstVoyage.setDestinationPoint("Moskov");
        firstVoyage.setDeparturePoint("Pskov");
        VoyageDto secondVoyage = new VoyageDto();
        secondVoyage.setDestinationPoint("Moskov");
        secondVoyage.setDeparturePoint("Pskov");
        VoyageDto thirdVoyage = new VoyageDto();
        thirdVoyage.setDestinationPoint("Moskov");
        thirdVoyage.setDeparturePoint("Pskov");

        voyageService.save(firstVoyage);
        voyageService.save(secondVoyage);
        voyageService.save(thirdVoyage);

        int expected = 3;

        int actual = voyageService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getVoyage() {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(new WarehouseDto());
        VoyageDto voyageToTest = new VoyageDto();
        voyageToTest.setDestinationPoint("Moskov");
        voyageToTest.setDeparturePoint("Pskov");
        voyageToTest.setDestinationPoint("Moskov");
        voyageToTest.setDeparturePoint("Pskov");
        GregorianCalendar expectedTime = new GregorianCalendar();

        expectedTime.set(Calendar.HOUR_OF_DAY, 15);
        expectedTime.set(Calendar.MINUTE, 30);

        Voyage expected = voyageService.save(voyageToTest);

        Voyage actual = voyageService.findOne(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        VoyageDto expectedVoyage = new VoyageDto();
        expectedVoyage.setDestinationPoint("Moskov");
        expectedVoyage.setDeparturePoint("Pskov");
        GregorianCalendar sentAt = new GregorianCalendar();
        sentAt.set(Calendar.HOUR_OF_DAY, 12);
        sentAt.set(Calendar.MINUTE, 30);

        Voyage savedVoyage = voyageService.save(expectedVoyage);

        String expected = "Poland";
        savedVoyage.setDestinationPoint(expected);

        Voyage actualVoyage = voyageService.update(modelMapper.map(savedVoyage, VoyageDto.class));

        VoyageDto actualDto = modelMapper.map(actualVoyage, VoyageDto.class);

        Assertions.assertEquals(expected, actualDto.getDestinationPoint());
    }
}
