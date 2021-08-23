package org.jazzteam.eltay.gasimov.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProcessingPointDto extends AbstractBuildingDto {
    @NotEmpty(message = "Warehouse which connected with processing point cannot be empty")
    @ToString.Exclude
    private WarehouseDto warehouse;
}
