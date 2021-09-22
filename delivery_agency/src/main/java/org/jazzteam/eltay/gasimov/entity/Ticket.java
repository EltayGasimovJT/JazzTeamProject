package org.jazzteam.eltay.gasimov.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "order")
@Entity
@Table(name = "orders_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "generated_ticket_number")
    private String ticketNumber;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Order order;
}
