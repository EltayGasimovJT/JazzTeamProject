package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WarehouseDto {
    private Long id;
    private List<OrderProcessingPointDto> orderProcessingPoints;
}