package entity;

import org.junit.Assert;
import org.junit.Test;

public class TreeTest {

    @Test
    public void getCountOfLeafTest() {
        int expected = 2;

        Tree tree = new Tree();
        tree.setRoot(10);
        tree.getRoot().add(9);
        tree.getRoot().add(11);


        int actual = tree.getRoot().getCountOfLeaf();


        Assert.assertEquals(expected, actual, 0.001);
    }
}