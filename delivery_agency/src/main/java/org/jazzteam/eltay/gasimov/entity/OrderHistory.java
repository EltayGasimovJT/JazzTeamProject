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
@Table
public class OrderHistory {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    @Column
    private String comment;
    @Column
    private Calendar changedAt;
    @Column
    private Calendar sentAt;
    @Column
    private String changedTypeEnum;
}
