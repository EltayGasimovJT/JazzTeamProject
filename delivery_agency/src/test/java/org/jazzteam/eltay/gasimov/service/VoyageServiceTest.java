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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class VoyageServiceTest {
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void addVoyage() {
        VoyageDto expected = new VoyageDto();
        expected.setDeparturePoint("Minsk");
        expected.setDestinationPoint("Moscow");

        VoyageDto actual = modelMapper.map(voyageService.save(expected), VoyageDto.class);
        expected.setId(actual.getId());
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
        Voyage savedSecond = voyageService.save(secondVoyageToTest);
        Voyage savedThird = voyageService.save(thirdVoyageToTest);

        voyageService.delete(firstToDelete.getId());
        List<Voyage> actual = voyageService.findAll();

        Assertions.assertEquals(Arrays.asList(savedSecond, savedThird), actual);
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

        Voyage savedFirst = voyageService.save(firstVoyage);
        Voyage savedSecond = voyageService.save(secondVoyage);
        Voyage savedThird = voyageService.save(thirdVoyage);

        List<Voyage> actual = voyageService.findAll();

        Assertions.assertEquals(Arrays.asList(savedFirst, savedSecond, savedThird), actual);
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
        VoyageDto expectedVoyageDto = new VoyageDto();
        expectedVoyageDto.setDestinationPoint("Moskov");
        expectedVoyageDto.setDeparturePoint("Pskov");
        GregorianCalendar sentAt = new GregorianCalendar();
        sentAt.set(Calendar.HOUR_OF_DAY, 12);
        sentAt.set(Calendar.MINUTE, 30);

        Voyage expectedVoyage = voyageService.save(expectedVoyageDto);

        String expected = "Poland";
        expectedVoyage.setDestinationPoint(expected);

        Voyage actualVoyage = voyageService.update(modelMapper.map(expectedVoyage, VoyageDto.class));

        Assertions.assertEquals(expectedVoyage, actualVoyage);
    }
}
