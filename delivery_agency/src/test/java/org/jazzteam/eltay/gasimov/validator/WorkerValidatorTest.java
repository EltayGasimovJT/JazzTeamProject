package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class WorkerValidatorTest {

    private static Stream<Arguments> testDataForValidate() {
        Worker firstWorker = Worker.builder()
                .name(null)
                .surname("qwr")
                .password("wqrqw")
                .workingPlace(new OrderProcessingPoint())
                .roles(Stream.of(WorkerRoles.builder().build()).collect(Collectors.toSet()))
                .build();
        Worker secondWorker = Worker.builder()
                .name("wqrwqw")
                .surname(null)
                .password("wqrqw")
                .workingPlace(new OrderProcessingPoint())
                .roles(Stream.of(WorkerRoles.builder().build()).collect(Collectors.toSet()))
                .build();
        Worker thirdWorker = Worker.builder()
                .name("wqrwqw")
                .surname("wqrqw")
                .password(null)
                .workingPlace(new OrderProcessingPoint())
                .roles(Stream.of(WorkerRoles.builder().build()).collect(Collectors.toSet()))
                .build();
        Worker fourthWorker = Worker.builder()
                .name("wqrwqw")
                .surname("wqrqw")
                .password("wq12")
                .workingPlace(new OrderProcessingPoint())
                .roles(null)
                .build();
        Worker sixthWorker = Worker.builder()
                .name("wqrwqw")
                .surname("wqrqw")
                .password("wq12")
                .workingPlace(null)
                .roles(Stream.of(WorkerRoles.builder().build()).collect(Collectors.toSet()))
                .build();
        Worker fifthWorker = null;

        return Stream.of(
                Arguments.of(firstWorker),
                Arguments.of(secondWorker),
                Arguments.of(thirdWorker),
                Arguments.of(fourthWorker),
                Arguments.of(fifthWorker),
                Arguments.of(sixthWorker)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateWorker(Worker worker) {
        try {
            WorkerValidator.validateWorker(worker);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnSave() {
        try {
            WorkerValidator.validateOnSave(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateUsersList() {
        try {
            WorkerValidator.validateWorkersList(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}