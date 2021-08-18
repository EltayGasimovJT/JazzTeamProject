package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private OrderStateDto state;
    private ParcelParametersDto parcelParameters;
    private ClientDto sender;
    private ClientDto recipient;
    private BigDecimal price;
    private OrderProcessingPointDto destinationPlace;
    private AbstractBuildingDto currentLocation;
    private List<OrderHistoryDto> history;
}