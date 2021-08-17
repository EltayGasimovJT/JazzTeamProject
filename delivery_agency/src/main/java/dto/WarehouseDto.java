package dto;

import entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto extends AbstractBuildingDto{
    private List<OrderProcessingPointDto> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;
}
