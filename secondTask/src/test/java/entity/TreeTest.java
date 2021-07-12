package entity;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class TreeTest {
    public static List<int[]> correctTestData() {
        List<int[]> testArraysForTreeCreation = new ArrayList<>();
        testArraysForTreeCreation.add(new int[]{10, 9, 11});
        testArraysForTreeCreation.add(new int[]{61, 623, 125, 256, 11, 0});
        testArraysForTreeCreation.add(new int[]{101, 92, 113, 25});

        return testArraysForTreeCreation;
    }

    @ParameterizedTest
    @MethodSource("correctTestData")
    public void getCountOfLeafTest(int[] testArraysForTreeCreation) {
        int expected = 2;

        Tree tree = new Tree(testArraysForTreeCreation);

        int actual = tree.getRoot().getCountOfLeaves();

        Assert.assertEquals(expected, actual, 0.001);
    }

}