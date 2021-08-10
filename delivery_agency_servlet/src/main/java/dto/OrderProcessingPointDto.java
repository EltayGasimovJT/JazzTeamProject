package dto;

import entity.AbstractBuilding;
import entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderProcessingPointDto extends AbstractBuilding {
    private Long id;
    private String location;
    private WarehouseDto warehouse;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
}
