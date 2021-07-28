package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter @NoArgsConstructor
public class OrderProcessingPoint extends AbstractBuilding {
    private Warehouse warehouse;

    public OrderProcessingPoint(long id, List<Order> expectedOrders, List<Order> dispatchedOrders, String location, Warehouse warehouse) {
        super(id, expectedOrders, dispatchedOrders, location);
        this.warehouse = warehouse;
    }
}
