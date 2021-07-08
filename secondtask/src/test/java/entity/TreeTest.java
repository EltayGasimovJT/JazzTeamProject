package entity;

import org.junit.Assert;
import org.junit.Test;

public class TreeTest {

    @Test
    public void getCountOfLeafTest() {
        int expected = 2;

        Tree tree = new Tree();
        tree.add(10);
        tree.add(9);
        tree.add(11);

        int actual = tree.getRoot().getCountOfLeaf();

        Assert.assertEquals(expected, actual, 0.001);
    }
}