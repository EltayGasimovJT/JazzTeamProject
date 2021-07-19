package entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ship extends Thread {
    private int containersToTake;
    private int containersToUpload;
    private final Port port;

    public Ship(String name, int containersToTake, int containersToLeave, Port port) {
        super(name);
        this.containersToTake = containersToTake;
        this.containersToUpload = containersToLeave;
        this.port = port;
        start();
    }

    @Override
    public void run() {
        port.increment();
        port.incrementCountOfCurrentShips();
        boolean isChanged = false;
        try {
            while (true) {
                if (!isChanged) {
                    port.askPermissionForTheShip();
                }
                isChanged = false;
                isChanged = uploadOrTakeContainers(isChanged);
                if (containersToTake == 0 && containersToUpload == 0) {
                    log.info(Thread.currentThread().getName() + " has finished his task");
                    port.returnPermission();
                    break;
                }
                if (isChanged) {
                    Thread.sleep(10);
                } else {
                    port.returnPermission();
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
                Thread.sleep(10);
            }
        } else {
            if (containersToTake != 0) {
                synchronized (port) {
                    isChanged = takeContainersFromThePort(isChanged);
                    Thread.sleep(10);
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
