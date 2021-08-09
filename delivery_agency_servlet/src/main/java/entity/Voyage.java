package entity;

import lombok.Data;

import java.util.Calendar;

@Data
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
