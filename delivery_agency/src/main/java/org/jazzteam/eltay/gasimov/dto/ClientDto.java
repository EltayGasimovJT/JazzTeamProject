package org.jazzteam.eltay.gasimov.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonManagedReference
    private Set<OrderDto> orders;
}
