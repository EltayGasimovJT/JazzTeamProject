package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractBuilding extends AbstractLocation {

    private String location;

    protected AbstractBuilding(long id, String location) {
        super(id);
        this.location = location;
    }

    protected AbstractBuilding(long id, List<Order> expectedOrders, List<Order> dispatchedOrders, String location) {
        super(id, expectedOrders, dispatchedOrders);
        this.location = location;
    }
}
