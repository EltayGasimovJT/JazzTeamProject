package entity;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Client {
    private long id;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;
    @Singular
    private List<Order> orders;

    public Client(long id, String name, String surName, String passportId, String phoneNumber, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.passportId = passportId;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addAllOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public void removeAllOrders(List<Order> orders) {
        this.orders.removeAll(orders);
    }

}
