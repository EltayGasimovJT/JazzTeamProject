package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.jazzteam.eltay.gasimov.util.Constants.OBJECT_NOT_FOUND_EXCEPTION;

class OrderValidatorTest {
    private static Stream<Arguments> testDataForValidate() {
        Order firstOrder = Order.builder()
                .sender(new Client())
                .price(BigDecimal.valueOf(-1))
                .destinationPlace(new OrderProcessingPoint())
                .state(new OrderState())
                .parcelParameters(new ParcelParameters())
                .currentLocation(new OrderProcessingPoint())
                .build();
        Order secondOrder = Order.builder()
                .sender(new Client())
                .price(BigDecimal.valueOf(10.0))
                .destinationPlace(null)
                .state(new OrderState())
                .parcelParameters(new ParcelParameters())
                .currentLocation(new OrderProcessingPoint())
                .build();
        Order thirdOrder = Order.builder()
                .sender(new Client())
                .price(BigDecimal.valueOf(10.0))
                .destinationPlace(new OrderProcessingPoint())
                .state(null)
                .parcelParameters(new ParcelParameters())
                .currentLocation(new OrderProcessingPoint())
                .build();
        Order fourthOrder = Order.builder()
                .sender(new Client())
                .price(BigDecimal.valueOf(10.0))
                .destinationPlace(new OrderProcessingPoint())
                .state(new OrderState())
                .parcelParameters(null)
                .currentLocation(new OrderProcessingPoint())
                .build();
        Order sixthsOrder = Order.builder()
                .sender(new Client())
                .price(BigDecimal.valueOf(10.0))
                .destinationPlace(new OrderProcessingPoint())
                .state(new OrderState())
                .parcelParameters(new ParcelParameters())
                .currentLocation(null)
                .build();
        Order fifthOrder = null;
        Order sevenths = Order.builder()
                .sender(null)
                .recipient(null)
                .price(BigDecimal.valueOf(-1))
                .destinationPlace(new OrderProcessingPoint())
                .state(new OrderState())
                .parcelParameters(new ParcelParameters())
                .currentLocation(new OrderProcessingPoint())
                .build();


        return Stream.of(
                Arguments.of(firstOrder),
                Arguments.of(secondOrder),
                Arguments.of(thirdOrder),
                Arguments.of(fourthOrder),
                Arguments.of(fifthOrder),
                Arguments.of(sixthsOrder),
                Arguments.of(sevenths)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateOrder(Order order) {
        try {
            OrderValidator.validateOrder(order);
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOrders() {
    }

    @Test
    void validateOnSave() {
    }

    @Test
    void validateOrdersOnTheWay() {
    }
}