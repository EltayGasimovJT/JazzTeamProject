package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "ticket")
@ToString(exclude = "ticket")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private OrderState state;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ParcelParameters parcelParameters;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private Client sender;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn
    private Client recipient;
    @Column
    private BigDecimal price;
    @Column(name = "track_number")
    private String trackNumber;
    @Column(name = "sending_time")
    private LocalDateTime sendingTime;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_point_id")
    private OrderProcessingPoint destinationPlace;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_point_id")
    private OrderProcessingPoint departurePoint;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn
    private AbstractBuilding currentLocation;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<OrderHistory> history;
    @JoinColumn(name = "ticket_id")
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Ticket ticket;
}