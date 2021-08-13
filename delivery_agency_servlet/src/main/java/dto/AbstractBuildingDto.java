package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AbstractBuildingDto {
    private String location;
    private List<WarehouseDto> warehouse;
    private List<OrderProcessingPointDto> processingPoints;
}
