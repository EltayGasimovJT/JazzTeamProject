package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoyageDto extends AbstractLocationDto{
    @NotBlank(message = "User surname cannot be empty")
    @Size(min = 1, max = 50, message = "Departure point size must be between 1 and 50 characters")
    private String departurePoint;
    @NotBlank(message = "User surname cannot be empty")
    @Size(min = 1, max = 50, message = "Destination point size must be between 1 and 50 characters")
    private String destinationPoint;
    @NotEmpty(message = "Sending time must be set")
    private Calendar sendingTime;
}
