package dto;

import lombok.Data;

@Data
public class OrderProcessingPointDto extends AbstractBuildingDto{
    private Long id;
    private WarehouseDto warehouse;
}
