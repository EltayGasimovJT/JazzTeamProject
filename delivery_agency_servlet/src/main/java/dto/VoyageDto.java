package dto;

import entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
@Builder
public class VoyageDto {
    private Long id;
    private String location;
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
}
