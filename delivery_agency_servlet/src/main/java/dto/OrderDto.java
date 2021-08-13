package dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private OrderStateDto state;
    private ParcelParametersDto parcelParameters;
    private ClientDto sender;
    private ClientDto recipient;
    private BigDecimal price;
    private OrderProcessingPointDto destinationPlace;
    private AbstractLocationDto currentLocation;
    private OrderHistoryDto history;
    private Calendar sendingTime;
    private List<AbstractLocationDto> route;
}

