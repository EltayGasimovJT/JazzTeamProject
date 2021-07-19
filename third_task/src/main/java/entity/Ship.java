package entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ship extends Thread {
    private int containersToTake;
    private int containersToUpload;
    private Port port;

    public Ship(String name, int containersToTake, int containersToLeave, Port port) {
        super(name);
        this.containersToTake = containersToTake;
        this.containersToUpload = containersToLeave;
        this.port = port;
        start();
    }

    @Override
    public void run() {
        port.incrementCountOfCurrentShips();
        port.increment();
        boolean isChanged = false;
        try {
            while (true) {
                if (!isChanged) {
                    port.askPermissionForTheShip();
                }
                isChanged = false;
                if (containersToUpload != 0 && containersToTake != 0) {
                    isChanged = uploadAndTakeContainers();
                } else {
                    if (containersToUpload != 0) {
                        synchronized (port) {
                            isChanged = uploadContainerIfThereAreContainersNotExceedingCapacity(isChanged);
                        }
                    } else {
                        if (containersToTake != 0) {
                            synchronized (port) {
                                isChanged = takeContainersIfThereAreContainersQtyNotZero(isChanged);
                            }
                        } else {
                            log.info(Thread.currentThread().getName() + " has finished his task");
                            port.returnPermission();
                            break;
                        }
                    }
                }

                if (isChanged) {
                    Thread.sleep(10);
                } else {
                    Thread.sleep(200);
                    port.returnPermission();
                }
            }

        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private boolean uploadAndTakeContainers() {
        boolean isChanged;
        containersToTake--;
        containersToUpload--;
        isChanged = true;
        return isChanged;
    }

    private boolean uploadContainerIfThereAreContainersNotExceedingCapacity(boolean isChanged) {
        if (port.getContainersCapacity() > port.getCurrentContainersQty()) {
            port.takeContainer();
            containersToUpload--;
            isChanged = true;
        }
        return isChanged;
    }

    private boolean takeContainersIfThereAreContainersQtyNotZero(boolean isChanged) {
        if (port.getCurrentContainersQty() > 0) {
            port.uploadContainer();
            containersToTake--;
            isChanged = true;
        }
        return isChanged;
    }
}
