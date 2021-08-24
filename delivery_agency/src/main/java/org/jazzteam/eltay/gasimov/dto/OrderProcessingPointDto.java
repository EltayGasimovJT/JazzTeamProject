package org.jazzteam.eltay.gasimov.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "warehouse")
@EqualsAndHashCode(exclude = "warehouse", callSuper = false)
public class OrderProcessingPointDto extends AbstractBuildingDto {
    @NotEmpty(message = "Warehouse which connected with processing point cannot be empty")
    private WarehouseDto warehouse;
}
