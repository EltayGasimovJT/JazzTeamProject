package dto;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseDto extends AbstractBuildingDto{
    private Long id;
    private List<OrderProcessingPointDto> orderProcessingPoints;
}
