package entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertSame(Thread.State.RUNNABLE, ships.get(0).getState());
        Assert.assertSame(Thread.State.RUNNABLE, ships.get(1).getState());
        Assert.assertSame(Thread.State.NEW, ships.get(2).getState());
        Assert.assertSame(Thread.State.NEW, ships.get(3).getState());

        Assert.assertEquals(100, port.getCurrentContainersQty());
        Assert.assertEquals(0, ships.get(0).getContainersToTake());
        Assert.assertEquals(500, ships.get(0).getContainersToUpload());
        Assert.assertEquals(500, ships.get(1).getContainersToUpload());
        Assert.assertEquals(0, ships.get(1).getContainersToTake());

        ships.get(2).start();

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        ships.get(3).start();

        Assert.assertEquals(249, port.getCurrentContainersQty());
        Assert.assertEquals(0, ships.get(0).getContainersToTake());
        Assert.assertEquals(0, ships.get(1).getContainersToTake());

        Assert.assertSame(Thread.State.TERMINATED, ships.get(0).getState());
        Assert.assertSame(Thread.State.TERMINATED, ships.get(1).getState());
        Assert.assertSame(Thread.State.TERMINATED, ships.get(2).getState());
        Assert.assertSame(Thread.State.RUNNABLE, ships.get(3).getState());

        Assert.assertEquals(90, port.getCurrentContainersQty());
        Assert.assertEquals(0, ships.get(2).getContainersToTake());
        Assert.assertEquals(30, ships.get(3).getContainersToUpload());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        Assert.assertEquals(120, port.getCurrentContainersQty());
        Assert.assertEquals(0, ships.get(2).getContainersToTake());
        Assert.assertEquals(0, ships.get(3).getContainersToUpload());

        Assert.assertSame(Thread.State.TERMINATED, ships.get(2).getState());
        Assert.assertSame(Thread.State.TERMINATED, ships.get(3).getState());
    }
}