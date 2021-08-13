package dto;

import entity.Order;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WarehouseDto {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private String location;
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;
}