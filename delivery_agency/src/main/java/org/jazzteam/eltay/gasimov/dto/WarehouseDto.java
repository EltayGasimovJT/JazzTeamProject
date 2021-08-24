package org.jazzteam.eltay.gasimov.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orderProcessingPoints")
@EqualsAndHashCode(exclude = "orderProcessingPoints", callSuper = false)
public class WarehouseDto extends AbstractBuildingDto {
    @NotEmpty(message = "Warehouse must have at least one connected processing point")
    private List<OrderProcessingPointDto> orderProcessingPoints;
    @NotEmpty(message = "Warehouse must have at least one connected warehouses")
    private List<WarehouseDto> connectedWarehouses;
}
