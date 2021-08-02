package entity;

import lombok.*;

import java.util.List;


@Data
public abstract class AbstractLocation {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
}
