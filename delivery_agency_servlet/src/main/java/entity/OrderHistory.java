package entity;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Builder
@Data
public class OrderHistory {
    private Order order;
    private User user;
    private String comment;
    private List<OrderHistory> history;
    private Calendar changingTime;
    private OrderStateChangeType changedTypeEnum;
}