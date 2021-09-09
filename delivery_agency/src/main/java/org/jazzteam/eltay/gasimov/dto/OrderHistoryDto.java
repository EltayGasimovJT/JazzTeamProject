package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jazzteam.eltay.gasimov.entity.OrderStateChangeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private Long id;
    @NotEmpty(message = "OrderHistory must have worker, who worked with")
    private WorkerDto worker;
    @NotBlank(message = "Comment cannot be null")
    @Size(min = 1, max = 50, message = "Comment size must be between 1 and 50 characters")
    private String comment;
    @NotEmpty(message = "OrderHistory must have value")
    private LocalDateTime changedAt;
    @NotEmpty(message = "OrderHistory must have value")
    private LocalDateTime sentAt;
    @NotEmpty(message = "Changed type must have value")
    private OrderStateChangeType changedTypeEnum;
}
