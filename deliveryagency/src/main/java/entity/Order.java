package entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Order {
    private int id;
    private OrderState state;
    private ParcelParameters parcelParameters;
    private Client sender;
    private Client recipient;
    private BigDecimal prise;
    private OrderProcessingPoint destinationPlace;
    private AbstractLocation currentLocation;
    private OrderHistory history;

    private List<AbstractLocation> route;

}

