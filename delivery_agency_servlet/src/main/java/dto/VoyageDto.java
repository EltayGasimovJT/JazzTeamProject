package dto;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class VoyageDto {
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}