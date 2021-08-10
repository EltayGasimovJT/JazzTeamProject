package dto;

import entity.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Builder
@Data
public class OrderDto {
    private Long id;
    private OrderState state;
    private ParcelParameters parcelParameters;
    private Client sender;
    private Client recipient;
    private BigDecimal price;
    private OrderProcessingPointDto destinationPlace;
    private AbstractLocation currentLocation;
    private Calendar sendingTime;
    private List<OrderHistory> history;
    private List<AbstractLocation> route;
}

