package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "abstractBuilding")
public abstract class AbstractBuilding extends AbstractLocation {
    private String location;
    private WorkingPlaceType workingPlaceType;
}
