package entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {
    private int id;
    private OrderState state;
    private ParcelParameters parcelParameters;
    private Client sender;
    private Client recipient;
    private BigDecimal prise;
    private AbstractBuilding destinationPlace;
    private AbstractLocation currentLocation;
    private OrderHistory history;

    private List<AbstractLocation> route;

    public Order(
            int id,
            OrderState state,
            ParcelParameters parcelParameters,
            Client sender,
            Client recipient,
            BigDecimal prise,
            AbstractBuilding destinationPlace,
            AbstractLocation currentLocation,
            OrderHistory history,
            List<AbstractLocation> route
    ) {
        this.id = id;
        this.state = state;
        this.parcelParameters = parcelParameters;
        this.sender = sender;
        this.recipient = recipient;
        this.prise = prise;
        this.destinationPlace = destinationPlace;
        this.currentLocation = currentLocation;
        this.history = history;
        this.route = route;
    }
}

