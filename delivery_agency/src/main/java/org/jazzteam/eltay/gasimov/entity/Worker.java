package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @ManyToOne(cascade = CascadeType.MERGE)
    private AbstractBuilding workingPlace;
    @ManyToMany(mappedBy = "workers", cascade = CascadeType.MERGE)
    @JsonManagedReference
    private Set<WorkerRoles> roles;
}