package dto;

import entity.AbstractBuilding;
import entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderProcessingPointDTO extends AbstractBuilding {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private String location;
    private WarehouseDTO warehouse;
}
