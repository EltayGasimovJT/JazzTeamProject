package entity;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Port {
    private int dockQty;
    private int containersCapacity;
    private int currentContainersQty;
    private int counter;

    List<Thread> ships = new ArrayList<>();

    public Port(int dockQty, int containersCapacity, int currentContainersQty) {
        this.dockQty = dockQty;
        this.containersCapacity = containersCapacity;
        this.currentContainersQty = currentContainersQty;
    }

    public int getContainersCapacity() {
        return containersCapacity;
    }

    public int getCurrentContainersQty() {
        return currentContainersQty;
    }

    public void takeContainer() {
        currentContainersQty++;
    }

    public void uploadContainer() {
        currentContainersQty--;
    }

    public synchronized void askPermissionForTheShip() {
        while (dockQty == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        ships.add(Thread.currentThread());

        log.info(Thread.currentThread().getName() + " has received permission");

        dockQty--;
    }

    public int getCounter() {
        return counter;
    }

    public void increment() {
        this.counter++;
    }

    public synchronized void returnPermission() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        log.info(Thread.currentThread().getName() + " is leaving dock");

        log.info("Current containers Qty in Port: " + currentContainersQty);

        if (ships.contains(Thread.currentThread())) {
            dockQty++;
        }
        ships.remove(Thread.currentThread());

        notifyAll();
    }
}
