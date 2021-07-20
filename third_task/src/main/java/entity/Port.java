package entity;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Port {
    private final int containersCapacity;
    private int currentShipsInDock;
    private int dockQty;
    private int currentContainersQty;
    private int counter;
    List<Thread> ships = new ArrayList<>();

    {
        currentShipsInDock = 0;
    }

    public Port(int dockQty, int containersCapacity, int currentContainersQty) {
        this.dockQty = dockQty;
        this.containersCapacity = containersCapacity;
        this.currentContainersQty = currentContainersQty;
    }

    public int getContainersCapacity() {
        return containersCapacity;
    }

    public int getCurrentShipsInDock() {
        return currentShipsInDock;
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
                Thread.currentThread().interrupt();
            }
        }

        ships.add(Thread.currentThread());

        log.info(Thread.currentThread().getName() + " has received permission");
        dockQty--;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCountOfCurrentShips() {
        currentShipsInDock++;
    }

    public void increment() {
        counter++;
    }

    public void decrementCountOfCurrentShips() {
        currentShipsInDock--;
    }

    public synchronized void returnPermission() {
        log.info(Thread.currentThread().getName() +
                " is leaving dock. Current containers Qty in Port: "
                + currentContainersQty);

        if (ships.contains(Thread.currentThread())) {
            dockQty++;
        }
        currentShipsInDock--;
        ships.remove(Thread.currentThread());
        notifyAll();
    }
}
