package dto;

import entity.Warehouse;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderProcessingPointDto extends AbstractBuildingDto{
    private Long id;
    private Warehouse warehouse;
}
