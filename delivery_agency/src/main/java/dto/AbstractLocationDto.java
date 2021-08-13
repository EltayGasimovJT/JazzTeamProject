package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AbstractLocationDto {
    private Long id;
    private List<OrderDto> expectedOrders;
    private List<OrderDto> dispatchedOrders;
}
