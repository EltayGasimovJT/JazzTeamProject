package dto;

import entity.OrderStateChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    @NotEmpty(message = "OrderHistory must have user, who worked with")
    private UserDto user;
    @NotBlank(message = "Comment cannot be null")
    @Size(min = 1, max = 50, message = "Comment size must be between 1 and 50 characters")
    private String comment;
    @NotEmpty(message = "OrderHistory must have value")
    private Calendar changingTime;
    @NotEmpty(message = "Changed type must have value")
    private OrderStateChangeType changedTypeEnum;
}