package entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class Order {
    private Long id;
    private OrderState state;
    private ParcelParameters parcelParameters;
    private Client sender;
    private Client recipient;
    private BigDecimal price;
    private OrderProcessingPoint destinationPlace;
    private AbstractLocation currentLocation;
    private OrderHistory history;

    private List<AbstractLocation> route;

}

