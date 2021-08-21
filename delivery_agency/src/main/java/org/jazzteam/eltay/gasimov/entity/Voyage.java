package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voyage")
public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private Calendar sendingTime;
}
