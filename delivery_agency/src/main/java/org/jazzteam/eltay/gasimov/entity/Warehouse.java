package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "orderProcessingPoints")
@Table(name = "warehouse")
public class Warehouse extends AbstractBuilding {
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderProcessingPoint> orderProcessingPoints;
}
