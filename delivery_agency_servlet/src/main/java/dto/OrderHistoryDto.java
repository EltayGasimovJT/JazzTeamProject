package dto;

import entity.Order;
import entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;


@Data
@Builder
public class OrderHistoryDto {
    private Order order;
    private User user;
    private String comment;
    private Calendar changingTime;
}
