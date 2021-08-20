package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {
    private User user;
    private String comment;
    private Calendar changingTime;
    private OrderStateChangeType changedTypeEnum;
}