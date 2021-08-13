package dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AbstractLocationDto {
    private List<OrderDto> expectedOrders;
    private List<OrderDto> dispatchedOrders;
}
