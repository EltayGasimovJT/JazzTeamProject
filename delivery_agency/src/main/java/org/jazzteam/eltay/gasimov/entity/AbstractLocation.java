package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "abstract_location")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "expected_orders_id")
    private List<Order> expectedOrders;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dispatched_orders_id")
    private List<Order> dispatchedOrders;
}