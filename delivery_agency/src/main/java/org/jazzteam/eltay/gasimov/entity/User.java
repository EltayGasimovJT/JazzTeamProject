package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    private AbstractBuilding workingPlace;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRoles", joinColumns = {
            @JoinColumn(name = "userId")
    }, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<UserRoles> roles;
}