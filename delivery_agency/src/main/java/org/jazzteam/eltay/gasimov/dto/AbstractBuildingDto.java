package org.jazzteam.eltay.gasimov.dto;

import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractBuildingDto extends AbstractLocationDto {
    @NotEmpty(message = "Location cannot be empty")
    @Size(min = 1, max = 50, message = "Abstract building size must be between 1 and 50 characters")
    private String location;
    @NotNull(message = "Working place must be set")
    private WorkingPlaceType workingPlaceType;
}
