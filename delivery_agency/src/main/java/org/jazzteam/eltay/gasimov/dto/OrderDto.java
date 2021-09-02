package org.jazzteam.eltay.gasimov.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@ToString(exclude = {"sender", "recipient"})
@EqualsAndHashCode(exclude = "sender")
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
    private String orderTrackNumber;
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @NotEmpty(message = "SendingTime cannot be null")
    private LocalDateTime sendingTime;
    @NotEmpty(message = "Order must have destination place")
    private OrderProcessingPointDto destinationPlace;
    @NotEmpty(message = "Order must have current location")
    private AbstractBuildingDto currentLocation;
    private String personalCode;
    private List<OrderHistoryDto> history;
}