package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ClientDto {
    private Long id;
    @NotBlank(message = "Client name cannot be empty")
    @Size(min = 1, max = 50, message = "Client name size must be between 1 and 50 characters")
    private String name;
    @NotBlank(message = "Client surname cannot be empty")
    @Size(min = 1, max = 50, message = "Client surname size must be between 1 and 50 characters")
    private String surname;
    @NotBlank(message = "Client passportId cannot be empty")
    @Size(min = 1, max = 100, message = "Client passportId must be between 1 and 100 characters")
    private String passportId;
    @NotBlank(message = "Client phoneNumber cannot be empty")
    @Size(min = 1, max = 100, message = "Client phoneNumber must be between 1 and 100 characters")
    private String phoneNumber;
    @NotEmpty(message = "Client must have at least one order")
    private List<OrderDto> orders;
}
