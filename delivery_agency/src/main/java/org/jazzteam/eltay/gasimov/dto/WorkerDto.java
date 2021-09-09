package org.jazzteam.eltay.gasimov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jazzteam.eltay.gasimov.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    private Long id;
    @NotBlank(message = "Worker name cannot be empty")
    @Size(min = 1, max = 50, message = "Worker name size must be between 1 and 50 characters")
    private String name;
    @NotBlank(message = "Worker surname cannot be empty")
    @Size(min = 1, max = 50, message = "Worker surname size must be between 1 and 50 characters")
    private String surname;
    @NotBlank(message = "Worker name cannot be empty")
    @Size(min = 1, max = 50, message = "Worker name size must be between 1 and 50 characters")
    private String password;
    @NotEmpty(message = "Worker must have working place")
    @JsonProperty("workingPlace")
    private AbstractBuildingDto workingPlace;
    @NotEmpty(message = "Worker must have at least one role")
    private Role role;
}
