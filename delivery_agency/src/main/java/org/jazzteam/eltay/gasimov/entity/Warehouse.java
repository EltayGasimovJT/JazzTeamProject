package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "orderProcessingPoints")
@ToString(exclude = "orderProcessingPoints")
@Embeddable
@Table(name = "warehouse")
public class Warehouse extends AbstractBuilding {
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderProcessingPoint> orderProcessingPoints;
}
