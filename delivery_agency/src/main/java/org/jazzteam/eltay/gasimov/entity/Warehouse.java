package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse extends AbstractBuilding {
    @OneToMany
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;
}
