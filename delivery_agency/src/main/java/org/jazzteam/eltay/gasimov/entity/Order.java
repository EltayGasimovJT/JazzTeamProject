package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private OrderState state;
    @OneToOne
    private ParcelParameters parcelParameters;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonBackReference
    private Client sender;
    private Client recipient;
    @Column(name = "price")
    private BigDecimal price;
    private OrderProcessingPoint destinationPlace;
    private AbstractBuilding currentLocation;
    @OneToMany
    private Set<OrderHistory> history;
}