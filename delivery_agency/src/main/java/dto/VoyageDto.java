package dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class VoyageDto extends AbstractLocationDto{
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
