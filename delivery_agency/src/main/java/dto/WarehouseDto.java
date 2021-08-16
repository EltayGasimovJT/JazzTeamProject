package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto extends AbstractBuildingDto{
    private Long id;
    private List<OrderProcessingPointDto> orderProcessingPoints;
}
