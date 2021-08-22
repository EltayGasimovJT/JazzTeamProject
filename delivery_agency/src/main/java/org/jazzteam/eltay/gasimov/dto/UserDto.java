package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jazzteam.eltay.gasimov.entity.UserRoles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

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
    @NotEmpty(message = "User must have working place")
    private AbstractBuildingDto workingPlace;
    @NotEmpty(message = "User must have at least one role")
    private List<UserRoles> roles;
}
