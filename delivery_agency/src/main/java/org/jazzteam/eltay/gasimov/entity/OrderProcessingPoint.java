package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderProcessingPoint extends AbstractBuilding {
    @ManyToOne
    private Warehouse warehouse;
}
