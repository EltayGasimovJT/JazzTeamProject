package org.jazzteam.eltay.gasimov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jazzteam.eltay.gasimov.entity.Role;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "User name cannot be empty")
    @Size(min = 1, max = 50, message = "User name size must be between 1 and 50 characters")
    private String name;
    @NotBlank(message = "User surname cannot be empty")
    @Size(min = 1, max = 50, message = "User surname size must be between 1 and 50 characters")
    private String surname;
    @NotBlank(message = "User name cannot be empty")
    @Size(min = 1, max = 50, message = "User name size must be between 1 and 50 characters")
    private String password;
    @NotEmpty(message = "User must have working place")
    @JsonProperty("workingPlace")
    private WorkingPlaceType workingPlace;
    @NotEmpty(message = "User must have at least one role")
    private Role role;
}
