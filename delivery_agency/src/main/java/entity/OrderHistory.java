package entity;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Builder
@Data
public class OrderHistory {
    private User user;
    private String comment;
    private Calendar changingTime;
    private OrderStateChangeType changedTypeEnum;
}
