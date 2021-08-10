package dto;

import entity.AbstractBuilding;
import entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseDto extends AbstractBuilding {
    private Long id;
    private String location;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private List<OrderProcessingPointDto> orderProcessingPoints;
    private List<WarehouseDto> connectedWarehouses;
}
