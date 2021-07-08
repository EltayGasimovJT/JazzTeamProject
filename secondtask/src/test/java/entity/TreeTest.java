package entity;

import creator.TreeCreator;
import org.junit.Assert;
import org.junit.Test;

public class TreeTest {
    private static final int[] EXPECTED_ARRAY = {1, 2, 3, 4, 5, 6};

    /*@Test
    public void getCountOfLeafTest() {
        int expected = 2;

        Tree tree = new Tree();
        tree.add(10);
        tree.add(9);
        tree.add(11);

        int actual = tree.getRoot().getCountOfLeaf();

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void creatorAndCountOfLeafTest() {
        Tree expectedTree = new Tree();

        expectedTree.add(1);
        expectedTree.add(2);
        expectedTree.add(3);
        expectedTree.add(4);
        expectedTree.add(5);
        expectedTree.add(6);

        Tree actualTree = TreeCreator.createTree(EXPECTED_ARRAY);

        int expected = expectedTree.getRoot().getCountOfLeaf();
        int actual = actualTree.getRoot().getCountOfLeaf();

        Assert.assertEquals(expected, actual, 0.001);
    }*/
}