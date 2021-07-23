package entity;

public class OrderHistory {
    private long orderId;
    private long userId;
    private String comment;
    private Client.Order previousState;
    private Client.Order currentState;
    private String changingTime;
    private ChangedTypeEnum changedTypeEnum;

    public OrderHistory(
            long orderId,
            long userId,
            String comment,
            Client.Order previousState,
            Client.Order currentState,
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Client.Order getPreviousState() {
        return previousState;
    }

    public void setPreviousState(Client.Order previousState) {
        this.previousState = previousState;
    }

    public Client.Order getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Client.Order currentState) {
        this.currentState = currentState;
    }

    public String getChangingTime() {
        return changingTime;
    }

    public void setChangingTime(String changingTime) {
        this.changingTime = changingTime;
    }

    public ChangedTypeEnum getChangedTypeEnum() {
        return changedTypeEnum;
    }

    public void setChangedTypeEnum(ChangedTypeEnum changedTypeEnum) {
        this.changedTypeEnum = changedTypeEnum;
    }
}
