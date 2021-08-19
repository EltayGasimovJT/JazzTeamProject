package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
