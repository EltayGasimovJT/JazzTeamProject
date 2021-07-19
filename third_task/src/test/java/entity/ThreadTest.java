package entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ThreadTest {
    private static final int EXPECTED_CONTAINERS = 1080;
    private static final int EXPECTED_COUNT_OF_SHIPS = 2;

    @Test
    public void testCorrectThreadsProcessing() throws InterruptedException {
        Port port = new Port(2, 5000, 1000);

        List<Ship> ships = Arrays
                .asList(
                        new Ship("Ship " + 1, 260, 0, port),
                        new Ship("Ship " + 2, 0, 300, port),
                        new Ship("Ship " + 3, 260, 0, port),
                        new Ship("Ship " + 4, 0, 300, port)
                );

        for (Ship ship : ships) {
            if (port.getCounter() == 2) {
                Assert.assertEquals(EXPECTED_COUNT_OF_SHIPS, port.getCurrentShipsInDock());
            }
            ship.join();
        }
        Assert.assertEquals(EXPECTED_CONTAINERS, port.getCurrentContainersQty());
    }
}
