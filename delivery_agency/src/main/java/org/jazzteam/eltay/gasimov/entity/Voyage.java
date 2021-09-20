package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voyage")
public class Voyage extends AbstractLocation {
    @Column(name = "departure_point")
    private String departurePoint;
    @Column(name = "destination_point")
    private String destinationPoint;
    @Column(name = "sending_time")
    private Calendar sendingTime;
}
