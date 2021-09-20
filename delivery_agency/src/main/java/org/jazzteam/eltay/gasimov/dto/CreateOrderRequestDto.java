package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {
    private ClientDto sender;
    private ClientDto recipient;
    private WorkerDto workerDto;
    private ParcelParametersDto parcelParameters;
    private String destinationPoint;
    private BigDecimal price;
}
