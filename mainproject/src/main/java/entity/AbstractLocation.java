package entity;

import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public abstract class AbstractLocation {
    private long id;
    @Singular
    private List<Order> expectedOrders;
    @Singular
    private List<Order> dispatchedOrders;

    protected AbstractLocation(long id) {
        this.id = id;
    }

    protected AbstractLocation(long id, List<Order> expectedOrders, List<Order> dispatchedOrders) {
        this.id = id;
        this.expectedOrders = expectedOrders;
        this.dispatchedOrders = dispatchedOrders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addExpectedOrder(Order order) {
        expectedOrders.add(order);
    }

    public void addAllExpectedOrders(List<Order> orders) {
        expectedOrders.addAll(orders);
    }

    public void removeExpectedOrder(Order order) {
        expectedOrders.remove(order);
    }

    public void removeAllExpectedOrders(List<Order> orders) {
        expectedOrders.removeAll(orders);
    }

    public void addDispatchedOrder(Order order) {
        dispatchedOrders.add(order);
    }

    public void addAllDispatchedOrders(List<Order> orders) {
        dispatchedOrders.addAll(orders);
    }

    public void removeDispatchedOrder(Order order) {
        dispatchedOrders.remove(order);
    }

    public void removeAllDispatchedOrders(List<Order> orders) {
        dispatchedOrders.removeAll(orders);
    }
}
