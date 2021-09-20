package org.jazzteam.eltay.gasimov.entity;


import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.validator.PortValidator;

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

    public Port() {
        currentShipsInDock = 0;
        containersCapacity = 0;
    }

    public int getDockQty() {
        return dockQty;
    }

    public void setDockQty(int dockQty) {
        this.dockQty = dockQty;
    }

    public Port(int dockQty, int containersCapacity, int currentContainersQty) {
        PortValidator.isPortCanBeCreated(dockQty, containersCapacity, currentContainersQty);
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

    public synchronized void askPermissionForTheShip(Ship ship) throws IllegalArgumentException {
        PortValidator.isShipsContainersMoreThanPortCapacity(this, ship);
        while (dockQty == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        if (ship.getContainersToUpload() > containersCapacity || ship.getContainersToTake() > containersCapacity) {
            ship.interrupt();
            throw new IllegalArgumentException("Out of port capacity!!!");
        }
        ships.add(ship);

        log.info(ship.getName() + " has received permission");
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

    public synchronized void returnPermission(Ship ship) {
        log.info(Thread.currentThread().getName() +
                " is leaving dock. Current containers Qty in Port: "
                + currentContainersQty);

        if (ships.contains(ship)) {
            dockQty++;
        }
        currentShipsInDock--;
        ships.remove(ship);
        ship.interrupt();
        notifyAll();
    }
}
