package dto;

import entity.OrderStateChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private UserDto user;
    private String comment;
    private Calendar changingTime;
    private OrderStateChangeType changedTypeEnum;
}