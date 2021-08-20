package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProcessingPointDto extends AbstractBuildingDto {
    @NotEmpty(message = "Warehouse which connected with processing point cannot be empty")
    private WarehouseDto warehouse;
}
