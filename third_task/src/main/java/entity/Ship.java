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
        boolean isChanged = false;

        try {

            while (true) {
                if (!isChanged) {
                    port.askPermissionForTheShip();
                }

                isChanged = false;

                if (containersToUpload != 0 && containersToTake != 0) {
                    containersToTake--;
                    containersToUpload--;
                    isChanged = true;
                } else {
                    if (containersToUpload != 0) {
                        synchronized (port) {
                            if (port.getContainersCapacity() > port.getCurrentContainersQty()) {
                                port.takeContainer();
                                containersToUpload--;
                                isChanged = true;
                            }
                        }
                    } else {
                        if (containersToTake != 0) {
                            synchronized (port) {
                                if (port.getCurrentContainersQty() > 0) {
                                    port.uploadContainer();
                                    containersToTake--;
                                    isChanged = true;
                                }
                            }
                        } else {
                            log.info(Thread.currentThread().getName() + " has finished his task");
                            port.returnPermission();
                            port.increment();
                            break;
                        }
                    }
                }

                if (isChanged) {
                    Thread.sleep(10);
                } else {
                    port.returnPermission();
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

    }
}
