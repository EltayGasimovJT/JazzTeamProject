package entity;

public class OrderProcessingPoint {
    private Warehouse warehouse;

    public OrderProcessingPoint(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
