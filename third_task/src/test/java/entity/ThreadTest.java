package entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ThreadTest {
    private static final int EXPECTED_COUNT_OF_SHIPS = 4;
    private static final int EXPECTED_CURRENT_SHIPS_IN_DOCK_AT_THE_BEGINNING = 2;
    private static final int EXPECTED_CURRENT_SHIPS_IN_DOCK_IN_THE_END = 1;

    @Test
    public void testCorrectThreadsProcessing() {
        Port port = new Port(2, 5000, 1000);

        List<Ship> ships = Arrays
                .asList(
                        new Ship("Ship " + 1, 260, 0, port),
                        new Ship("Ship " + 2, 0, 300, port),
                        new Ship("Ship " + 3, 260, 0, port),
                        new Ship("Ship " + 4, 0, 300, port)
                );

        ships.get(0).start();
        ships.get(1).start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        if (port.getCounter() == 2) {
            Assert.assertEquals(EXPECTED_CURRENT_SHIPS_IN_DOCK_AT_THE_BEGINNING, port.getCurrentShipsInDock());
        }

        ships.get(2).start();
        ships.get(3).start();

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        if (port.getCounter() == 4) {
            Assert.assertEquals(EXPECTED_CURRENT_SHIPS_IN_DOCK_IN_THE_END, port.getCurrentShipsInDock());
        }

        Assert.assertEquals(EXPECTED_COUNT_OF_SHIPS, port.getCounter());
    }
}