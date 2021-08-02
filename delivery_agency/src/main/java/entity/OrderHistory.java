package entity;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderHistory {
    private Long orderId;
    private Long userId;
    private String comment;
    private List<OrderState> allStates;
    private String changingTime;
    private ChangedTypeEnum changedTypeEnum;
}
