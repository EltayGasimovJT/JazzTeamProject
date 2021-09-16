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
@Table(name = "order_state")
public class OrderState {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "state")
    private String state;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "roles_allowed_withdraw_from_state", joinColumns = {
            @JoinColumn(name = "order_state_id")
    }, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<WorkerRoles> rolesAllowedWithdrawFromState;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "roles_allowed_put_to_state", joinColumns = {
            @JoinColumn(name = "order_state_id")
    }, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<WorkerRoles> rolesAllowedPutToState;

    @Column(name = "history_prefix")
    private String prefix;

    @Column(name = "history_suffix")
    private String suffix;
}
