package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public abstract class AbstractBuilding extends AbstractLocation {
    @Getter
    @Setter
    private String location;

    public AbstractBuilding(long id, String location) {
        super(id);
        this.location = location;
    }

    public AbstractBuilding(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String location) {
        super(id, expectedOrders, dispatchedOrders);
        this.location = location;
    }
}
