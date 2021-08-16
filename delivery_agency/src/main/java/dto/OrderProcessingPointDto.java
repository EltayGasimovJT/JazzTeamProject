package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProcessingPointDto extends AbstractBuildingDto{
    private WarehouseDto warehouse;
}
