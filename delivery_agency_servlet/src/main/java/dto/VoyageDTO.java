package dto;

import entity.AbstractLocation;
import entity.Order;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class VoyageDTO extends AbstractLocation {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private String location;
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
