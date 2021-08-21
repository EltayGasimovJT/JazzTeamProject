package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderHistory")
public class OrderHistory {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @Column(name = "comment")
    private String comment;
    @Column(name = "changedAt")
    private Calendar changedAt;
    @ManyToOne
    private OrderStateChangeType changedTypeEnum;
}
