package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto extends AbstractBuildingDto{
    @NotEmpty(message = "Warehouse must have at least one connected processing point")
    @EqualsAndHashCode.Exclude
    private List<OrderProcessingPointDto> orderProcessingPoints;
    @NotEmpty(message = "Warehouse must have at least one connected warehouses")
    private List<WarehouseDto> connectedWarehouses;
}
