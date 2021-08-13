package dto;

import entity.Order;
import entity.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderProcessingPointDto  {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private String location;
    private Warehouse warehouse;
}