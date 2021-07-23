package entity;

import java.util.List;

public abstract class AbstractBuilding extends AbstractLocation {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AbstractBuilding(long id, String location) {
        super(id);
        this.location = location;
    }

    public AbstractBuilding(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String location) {
        super(id, expectedOrders, dispatchedOrders);
        this.location = location;
    }
}
