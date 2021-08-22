package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderProcessingPoint")
public class OrderProcessingPoint extends AbstractBuilding {
    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;
}
