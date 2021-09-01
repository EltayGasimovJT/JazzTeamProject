package org.jazzteam.eltay.gasimov.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderRequestDto {
    private ClientDto sender;
    private ClientDto recipient;
    private UserDto userDto;
    private ParcelParametersDto parcelParameters;
    private String destinationPlace;
    private BigDecimal price;
}
