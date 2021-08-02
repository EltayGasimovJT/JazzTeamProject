package entity;

import lombok.Data;


@Data
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private String sendingTime;
}
