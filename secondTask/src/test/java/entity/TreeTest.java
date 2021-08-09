package entity;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TreeTest {

    private static Stream<Arguments> testArraysForCheckingCountOfLeaves() {
        return Stream.of(
                Arguments.of(new int[]{10, 9, 11}, 2),
                Arguments.of(new int[]{125, 51, 623, 61, 256, 240, 11}, 4),
                Arguments.of(new int[]{101, 92, 113, 265}, 2)
        );
    }

    private static Stream<Arguments> testArraysToCheckTreeConstructor() {
        Tree firstTreeToCheck = new Tree(new Node(10, new Node(9), new Node(11)));
        Tree secondTreeToCheck = new Tree(new Node(125, new Node(51, new Node(11),
                new Node(61)), new Node(240, new Node(125), new Node(256))));

        firstTreeToCheck.getRoot().setHeight(1);
        secondTreeToCheck.getRoot().setHeight(2);
        secondTreeToCheck.getRoot().getLeft().setHeight(1);
        secondTreeToCheck.getRoot().getRight().setHeight(1);
        return Stream.of(
                Arguments.of(new int[]{10, 9, 11}, firstTreeToCheck),
                Arguments.of(new int[]{125, 51, 623, 61, 256, 240, 11}, secondTreeToCheck)
        );
    }

    @ParameterizedTest
    @MethodSource("testArraysForCheckingCountOfLeaves")
    public void getCountOfLeafTest(int[] testArraysForTreeCreation, int expected) {
        Tree tree = new Tree(testArraysForTreeCreation);

        int actual = tree.getRoot().getCountOfLeaves();

        Assert.assertEquals(expected, actual, 0.001);
    }

    @ParameterizedTest
    @MethodSource("testArraysToCheckTreeConstructor")
    public void checkTreeConstructor(int[] testArraysForTreeCreation, Tree expectedTree) {
        Tree actualTree = new Tree(testArraysForTreeCreation);

        Assert.assertEquals(expectedTree, actualTree);
    }
}