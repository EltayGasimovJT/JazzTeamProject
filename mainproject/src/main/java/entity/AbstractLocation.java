package entity;

import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public abstract class AbstractLocation {
    private long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;

    protected AbstractLocation(long id) {
        this.id = id;
    }

    protected AbstractLocation(long id, List<Order> expectedOrders, List<Order> dispatchedOrders) {
        this.id = id;
        this.expectedOrders = expectedOrders;
        this.dispatchedOrders = dispatchedOrders;
    }
}
