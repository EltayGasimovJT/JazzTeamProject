package org.jazzteam.eltay.gasimov.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "passport_id")
    private String passportId;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonManagedReference
    private Set<Order> orders;

    @OneToOne
    private ClientsCode code;
}