package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoyageDto extends AbstractLocationDto{
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
