package dto;

import entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WarehouseDto {
    private Long id;
    private String location;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
    private List<OrderProcessingPointDto> orderProcessingPoints;
    private List<WarehouseDto> connectedWarehouses;
}
