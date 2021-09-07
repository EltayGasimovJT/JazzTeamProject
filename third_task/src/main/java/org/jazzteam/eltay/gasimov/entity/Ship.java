package org.jazzteam.eltay.gasimov.entity;

import lombok.extern.slf4j.Slf4j;
import org.jazzteam.eltay.gasimov.validator.ShipValidator;

@Slf4j
public class Ship extends Thread {
    private int containersToTake;
    private int containersToUpload;
    private final int shipCapacity;
    private final Port port;

    public int getShipCapacity() {
        return shipCapacity;
    }

    public Ship(String name, int shipCapacity, int containersToTake, int containersToLeave, Port port) {
        super(name);
        ShipValidator.isShipCanBeCreated(shipCapacity, containersToTake, containersToLeave);
        this.containersToTake = containersToTake;
        this.containersToUpload = containersToLeave;
        this.shipCapacity = shipCapacity;
        this.port = port;
    }

    public int getContainersToTake() {
        return containersToTake;
    }

    public void setContainersToTake(int containersToTake) {
        this.containersToTake = containersToTake;
    }

    public int getContainersToUpload() {
        return containersToUpload;
    }

    public void setContainersToUpload(int containersToUpload) {
        this.containersToUpload = containersToUpload;
    }

    public Port getPort() {
        return port;
    }

    @Override
    public void run() {
        boolean isChanged = false;
        port.increment();
        port.incrementCountOfCurrentShips();
        try {
            while (true) {
                if (!isChanged) {
                    port.askPermissionForTheShip(this);
                }
                isChanged = false;
                isChanged = uploadOrTakeContainers(isChanged);
                if (containersToTake == 0 && containersToUpload == 0) {
                    log.info(Thread.currentThread().getName() + " has finished his task");
                    port.returnPermission(this);
                    break;
                }
                if (isChanged) {
                    Thread.sleep(10);
                } else {
                    port.returnPermission(this);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private synchronized boolean uploadOrTakeContainers(boolean isChanged) throws InterruptedException {
        if (containersToUpload != 0) {
            synchronized (port) {
                isChanged = uploadContainerToThePort(isChanged);
                Thread.sleep(1);
            }
        } else {
            if (containersToTake != 0) {
                synchronized (port) {
                    isChanged = takeContainersFromThePort(isChanged);
                    Thread.sleep(1);
                }
            }
        }
        return isChanged;
    }

    private boolean uploadContainerToThePort(boolean isChanged) {
        if (port.getContainersCapacity() > port.getCurrentContainersQty()) {
            port.takeContainer();
            containersToUpload--;
            isChanged = true;
        }
        return isChanged;
    }

    private boolean takeContainersFromThePort(boolean isChanged) {
        if (port.getCurrentContainersQty() > 0) {
            port.uploadContainer();
            containersToTake--;
            isChanged = true;
        }
        return isChanged;
    }
}
