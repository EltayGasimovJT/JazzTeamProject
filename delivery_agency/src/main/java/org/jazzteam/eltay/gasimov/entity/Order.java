package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @JoinColumn
    private OrderState state;
    @OneToOne
    private ParcelParameters parcelParameters;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private Client sender;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn
    private Client recipient;
    @Column
    private BigDecimal price;
    @Column(name = "track_number")
    private String trackNumber;
    @Column(name = "sending_time")
    private LocalDateTime sendingTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "destination_point_id")
    private OrderProcessingPoint destinationPlace;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "departure_point_id")
    private OrderProcessingPoint departurePoint;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn
    private AbstractBuilding currentLocation;
    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<OrderHistory> history;
}