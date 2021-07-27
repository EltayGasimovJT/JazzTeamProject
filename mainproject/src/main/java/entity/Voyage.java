package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@Setter @Getter
@NoArgsConstructor
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private String sendingTime;

    public Voyage(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String departurePoint, String destinationPoint, String sendingTime) {
        super(id, expectedOrders, dispatchedOrders);
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.sendingTime = sendingTime;
    }
}
