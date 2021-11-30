package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ClientValidatorTest {

    private static Stream<Arguments> testDataForValidate() {
        Client firstClient = Client.builder()
                .name(null)
                .surname("qwr")
                .passportId("wqrqw")
                .phoneNumber("12312")
                .build();
        Client secondClient = Client.builder()
                .name("wqrwqw")
                .surname(null)
                .passportId("wqrqw")
                .phoneNumber("12312")
                .build();
        Client thirdClient = Client.builder()
                .name("wqrwqw")
                .surname("wqrqw")
                .passportId(null)
                .phoneNumber("12312")
                .build();
        Client fourthClient = Client.builder()
                .name("wqrwqw")
                .surname("wqrqw")
                .passportId("wq12")
                .phoneNumber(null)
                .build();
        Client fifthClient = null;


        return Stream.of(
                Arguments.of(firstClient),
                Arguments.of(secondClient),
                Arguments.of(thirdClient),
                Arguments.of(fourthClient),
                Arguments.of(fifthClient)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateClient(Client clientDto) {
        try {
            ClientValidator.validateClient(clientDto);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateClient() {
        try {
            ClientValidator.validateOnSave(null);
            fail(ILLEGAL_STATE_EXCEPTION);
        } catch (IllegalStateException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateClientList() {
        try {
            ClientValidator.validateClientList(Collections.emptyList());
            fail(OBJECT_NOT_FOUND_EXCEPTION);
            fail(ILLEGAL_STATE_EXCEPTION);
        } catch (IllegalStateException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindById() {
        try {
            ClientValidator.validateOnFindById(null, 1L);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindByPhoneNumber() {
        try {
            ClientValidator.validateOnFindByPassport(null, "1244");
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindByPassport() {
        try {
            ClientValidator.validateOnFindByPhoneNumber(null, "1244");
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}