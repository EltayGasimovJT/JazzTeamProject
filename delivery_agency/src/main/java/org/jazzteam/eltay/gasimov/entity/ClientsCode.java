package org.jazzteam.eltay.gasimov.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "client")
@Entity
@Table(name = "clients_code")
public class ClientsCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "generated_code")
    private String generatedCode;

    @OneToOne
    private Client client;
}
