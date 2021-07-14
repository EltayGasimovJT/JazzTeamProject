package entity;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TreeTest {

    private static Stream<Arguments> correctDataForTreeTest() {
        return Stream.of(
                Arguments.of(new int[]{10, 9, 11}, 2),
                Arguments.of(new int[]{61, 51, 623, 125, 256, 11}, 3),
                Arguments.of(new int[]{101, 92, 113, 265}, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("correctDataForTreeTest")
    public void getCountOfLeafTest(int[] testArraysForTreeCreation, int expected) {
        Tree tree = new Tree(new Node(testArraysForTreeCreation[0]));

        for (int i = 1; i < testArraysForTreeCreation.length; i++) {
            tree.insert(testArraysForTreeCreation[i]);
        }

        int actual = tree.getRoot().getCountOfLeaves();

        Assert.assertEquals(expected, actual, 0.001);
    }
}