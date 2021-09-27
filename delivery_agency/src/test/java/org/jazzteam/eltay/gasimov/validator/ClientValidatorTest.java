package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

class ClientValidatorTest {
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";
    public static final String OBJECT_NOT_FOUND_EXCEPTION = "Expected ObjectNotFoundException";

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
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateClientList() {
        try {
            ClientValidator.validateClient(null);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnSave() {
        try {
            ClientValidator.validateClientList(Collections.emptyList());
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindById() {
    }

    @Test
    void validateOnFindByPhoneNumber() {
    }

    @Test
    void validateOnFindByPassport() {
    }
}