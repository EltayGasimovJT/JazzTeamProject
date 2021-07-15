package entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    private static final int EXPECTED_COUNTER = 2;

    @Test
    public void testCorrectThreadsProcessing() {
        Port port = new Port(4, 5000, 1000);

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship("Ship " + 1, 260, 0, port));

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ships.add(new Ship("Ship " + 2, 0, 300, port));

        for (Ship ship : ships) {
            try {
                ship.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(EXPECTED_COUNTER, port.getCounter());
    }
}
