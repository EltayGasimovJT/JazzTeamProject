package entity;

import java.util.List;

public class OrderProcessingPoint extends AbstractBuilding{
    private Warehouse warehouse;

    public OrderProcessingPoint(long id, String location, Warehouse warehouse) {
        super(id, location);
        this.warehouse = warehouse;
    }

    public OrderProcessingPoint(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String location, Warehouse warehouse) {
        super(id, expectedOrders, dispatchedOrders, location);
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
