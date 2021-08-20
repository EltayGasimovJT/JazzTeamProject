package entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ThreadTest {
    private static final int EXPECTED_COUNT_OF_SHIPS = 0;

    @Test
    public void testCorrectThreadsProcessing() throws InterruptedException {
        Port port = new Port(2, 5000, 1000);

        List<Ship> ships = Arrays
                .asList(
                        new Ship("Ship " + 1, 500, 0, 260,  port),
                        new Ship("Ship " + 2, 500, 300, 0, port),
                        new Ship("Ship " + 3, 500, 0, 260, port),
                        new Ship("Ship " + 4, 500, 300, 0,port)
                );


        for (Ship ship : ships) {
            ship.start();
        }

        Assert.assertEquals(0, port.getCurrentShipsInDock());

        for (Ship ship : ships) {
            ship.join();
        }

        Assert.assertEquals(EXPECTED_COUNT_OF_SHIPS, port.getCurrentShipsInDock());
    }
}