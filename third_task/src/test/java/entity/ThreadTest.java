package entity;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.entity.Port;
import org.jazzteam.eltay.gasimov.entity.Ship;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ThreadTest {

    @Test
    public void testCorrectThreadsProcessing() throws InterruptedException {
        Port port = new Port(2, 5000, 100);

        List<Ship> ships = Arrays
                .asList(
                        new Ship("Ship " + 1, 600, 0,500, port),
                        new Ship("Ship " + 2, 600, 0,500, port),
                        new Ship("Ship " + 3, 600, 30,500, port),
                        new Ship("Ship " + 4, 600, 30,500, port)
                );

        ships.get(0).start();
        ships.get(1).start();

        Assertions.assertSame(Thread.State.RUNNABLE, ships.get(0).getState());
        Assertions.assertSame(Thread.State.RUNNABLE, ships.get(1).getState());
        Assertions.assertSame(Thread.State.NEW, ships.get(2).getState());
        Assertions.assertSame(Thread.State.NEW, ships.get(3).getState());

        Assertions.assertEquals(100, port.getCurrentContainersQty());
        Assertions.assertEquals(0, ships.get(0).getContainersToTake());
        Assertions.assertEquals(500, ships.get(0).getContainersToUpload());
        Assertions.assertEquals(500, ships.get(1).getContainersToUpload());
        Assertions.assertEquals(0, ships.get(1).getContainersToTake());

        ships.get(2).start();

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        ships.get(3).start();

        Assertions.assertEquals(249, port.getCurrentContainersQty());
        Assertions.assertEquals(0, ships.get(0).getContainersToTake());
        Assertions.assertEquals(0, ships.get(1).getContainersToTake());

        Assertions.assertSame(Thread.State.TERMINATED, ships.get(0).getState());
        Assertions.assertSame(Thread.State.TERMINATED, ships.get(1).getState());
        Assertions.assertSame(Thread.State.TERMINATED, ships.get(2).getState());
        Assertions.assertSame(Thread.State.RUNNABLE, ships.get(3).getState());

        Assertions.assertEquals(90, port.getCurrentContainersQty());
        Assertions.assertEquals(0, ships.get(2).getContainersToTake());
        Assertions.assertEquals(30, ships.get(3).getContainersToUpload());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        Assertions.assertEquals(120, port.getCurrentContainersQty());
        Assertions.assertEquals(0, ships.get(2).getContainersToTake());
        Assertions.assertEquals(0, ships.get(3).getContainersToUpload());

        Assertions.assertSame(Thread.State.TERMINATED, ships.get(2).getState());
        Assertions.assertSame(Thread.State.TERMINATED, ships.get(3).getState());
    }
}