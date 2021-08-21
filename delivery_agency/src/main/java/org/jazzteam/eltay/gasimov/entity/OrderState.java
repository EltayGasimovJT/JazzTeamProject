package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderState")
public class OrderState {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "state")
    private String state;
    @ManyToMany(mappedBy = "roles")
    private Set<String> rolesAllowedWithdrawFromState;
    @ManyToMany
    private Set<String> rolesAllowedPutToState;
}
