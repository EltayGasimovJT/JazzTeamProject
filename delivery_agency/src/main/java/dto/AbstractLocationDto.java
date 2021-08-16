package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractLocationDto {
    private Long id;
    private List<OrderDto> expectedOrders;
    private List<OrderDto> dispatchedOrders;
}