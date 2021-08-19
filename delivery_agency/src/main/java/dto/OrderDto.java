package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    @NotEmpty(message = "Order must have state")
    private OrderStateDto state;
    @NotEmpty(message = "Order must have parcelParameters")
    private ParcelParametersDto parcelParameters;
    @NotEmpty(message = "Order must Have sender")
    private ClientDto sender;
    @NotEmpty(message = "Order must Have recipient")
    private ClientDto recipient;
    @Positive(message = "Price must be positive")
    @NotEmpty(message = "Price cannot be empty")
    private BigDecimal price;
    @NotEmpty(message = "Order must have destination place")
    private OrderProcessingPointDto destinationPlace;
    @NotEmpty(message = "Order must have current location")
    private AbstractBuildingDto currentLocation;
    private List<OrderHistoryDto> history;
}