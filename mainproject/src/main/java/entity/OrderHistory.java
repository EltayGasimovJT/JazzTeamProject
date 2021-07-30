package entity;


import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private long orderId;
    private long userId;
    private String comment;
    private Order previousState;
    private Order currentState;
    private String changingTime;
    private ChangedTypeEnum changedTypeEnum;
}
