package entity;


import lombok.*;

@Builder
@Data
public class OrderHistory {
    private Long orderId;
    private Long userId;
    private String comment;
    private Order previousState;
    private Order currentState;
    private String changingTime;
    private ChangedTypeEnum changedTypeEnum;
}
