package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderProcessingPoint extends AbstractBuilding {
    private Warehouse warehouse;

    public OrderProcessingPoint(long id, String location, Warehouse warehouse) {
        super(id, location);
        this.warehouse = warehouse;
    }

    public OrderProcessingPoint(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String location, Warehouse warehouse) {
        super(id, expectedOrders, dispatchedOrders, location);
        this.warehouse = warehouse;
    }
}
