package entity;

import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public abstract class AbstractLocation {
    private long id;
    @Singular
    private List<Client.Order> expectedOrders;
    @Singular
    private List<Client.Order> dispatchedOrders;

    protected AbstractLocation(long id) {
        this.id = id;
    }

    protected AbstractLocation(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders) {
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

    public void addExpectedOrder(Client.Order order) {
        expectedOrders.add(order);
    }

    public void addAllExpectedOrders(List<Client.Order> orders) {
        expectedOrders.addAll(orders);
    }

    public void removeExpectedOrder(Client.Order order) {
        expectedOrders.remove(order);
    }

    public void removeAllExpectedOrders(List<Client.Order> orders) {
        expectedOrders.removeAll(orders);
    }

    public void addDispatchedOrder(Client.Order order) {
        dispatchedOrders.add(order);
    }

    public void addAllDispatchedOrders(List<Client.Order> orders) {
        dispatchedOrders.addAll(orders);
    }

    public void removeDispatchedOrder(Client.Order order) {
        dispatchedOrders.remove(order);
    }

    public void removeAllDispatchedOrders(List<Client.Order> orders) {
        dispatchedOrders.removeAll(orders);
    }
}