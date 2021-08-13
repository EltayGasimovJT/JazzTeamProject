package dto;

import entity.Warehouse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProcessingPointDto  {
    private Long id;
    private Warehouse warehouse;
}
