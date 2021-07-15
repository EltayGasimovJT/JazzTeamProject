package entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadTest {
    private static final int EXPECTED_COUNTER = 2;

    @Test
    public void testCorrectThreadsProcessing() {
        Port port = new Port(2, 5000, 1000);

        List<Ship> ships = new ArrayList<>();

        ships.add(new Ship("Ship " + 1, 260, 0, port));

        ships.add(new Ship("Ship " + 2, 0, 300, port));

        for (Ship ship : ships) {
            try {
                ship.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        Assert.assertEquals(EXPECTED_COUNTER, port.getCounter());
    }
}
