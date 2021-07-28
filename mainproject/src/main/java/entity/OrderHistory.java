package entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter @NoArgsConstructor
public class OrderHistory {
    private long orderId;
    private long userId;
    private String comment;
    private Order previousState;
    private Order currentState;
    private String changingTime;
    private ChangedTypeEnum changedTypeEnum;

    public OrderHistory(
            long orderId,
            long userId,
            String comment,
            Order previousState,
            Order currentState,
            String changingTime,
            ChangedTypeEnum changedTypeEnum
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.comment = comment;
        this.previousState = previousState;
        this.currentState = currentState;
        this.changingTime = changingTime;
        this.changedTypeEnum = changedTypeEnum;
    }
}
