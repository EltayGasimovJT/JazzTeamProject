package entity;


import java.util.ArrayList;
import java.util.List;

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

    public synchronized void askPermission() {
        while (dockQty == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ships.add(Thread.currentThread());

        System.out.println(Thread.currentThread().getName() + " has received permission");
        dockQty--;
    }

    public int getCounter() {
        return counter;
    }

    public void increment(){
        this.counter++;
    }

    public synchronized void returnPermission() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " is leaving dock");

        System.out.println("Current containers Qty in Port: " + currentContainersQty);

        if (ships.contains(Thread.currentThread())) {
            dockQty++;
        }
        ships.remove(Thread.currentThread());

        notifyAll();
    }
}
