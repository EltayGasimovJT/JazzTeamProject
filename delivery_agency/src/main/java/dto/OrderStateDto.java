package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStateDto {
    private Long id;
    @NotBlank(message = "OrderState must have value")
    @Size(min = 1, max = 80, message = "State size must be between 1 and 80 characters")
    private String state;
    @NotEmpty(message = "State must have at least one role, which allows withdraw from state")
    private List<String> rolesAllowedWithdrawFromState;
    @NotEmpty(message = "State must have at least one role, which allows put to state")
    private List<String> rolesAllowedPutToState;
}
