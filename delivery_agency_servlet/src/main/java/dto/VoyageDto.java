package dto;

import entity.AbstractLocation;
import entity.Order;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class VoyageDto extends AbstractLocation {
    private Long id;
    private String location;
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
}
