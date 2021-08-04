package entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderState {
    private Long id;
    private String state;
    private List<String> rolesAllowedWithdrawFromState;
    private List<String> rolesAllowedPutToState;
    private List<Order> orders;
}
