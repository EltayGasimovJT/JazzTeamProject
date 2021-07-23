package entity;

public class RouteBetweenWarehouses {
    private int distance;
    private String deliveryTime;
    private Warehouse acceptingWarehouse;

    public RouteBetweenWarehouses(int distance, String deliveryTime, Warehouse acceptingWarehouse) {
        this.distance = distance;
        this.deliveryTime = deliveryTime;
        this.acceptingWarehouse = acceptingWarehouse;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Warehouse getAcceptingWarehouse() {
        return acceptingWarehouse;
    }

    public void setAcceptingWarehouse(Warehouse acceptingWarehouse) {
        this.acceptingWarehouse = acceptingWarehouse;
    }


}
