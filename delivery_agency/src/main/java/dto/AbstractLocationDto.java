package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractLocationDto {
    private Long id;
    @NotEmpty(message = "Abstract location need at least one expectedOrder")
    private List<OrderDto> expectedOrders;
    @NotEmpty(message = "Abstract location need at least one dispatchedOrder")
    private List<OrderDto> dispatchedOrders;
}