package dto;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;


@Data
@Builder
public class OrderHistoryDto {
    private UserDto user;
    private String comment;
    private Calendar changingTime;
}
