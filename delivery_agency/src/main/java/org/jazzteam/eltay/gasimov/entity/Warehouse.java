package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "warehouse")
public class Warehouse extends AbstractBuilding {
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<OrderProcessingPoint> orderProcessingPoints;
}
