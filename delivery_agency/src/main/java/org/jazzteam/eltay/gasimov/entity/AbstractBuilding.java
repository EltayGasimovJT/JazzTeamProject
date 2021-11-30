package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Embeddable
@Table(name = "abstract_building")
public abstract class AbstractBuilding extends AbstractLocation {
    @Column(name = "location", unique = true)
    private String location;
    @Column(name = "working_place_type")
    private String workingPlaceType;
}
