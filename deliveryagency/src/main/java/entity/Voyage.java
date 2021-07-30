package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private String sendingTime;
}
