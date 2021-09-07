package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parcel_parameters")
public class ParcelParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "height")
    private Double height;
    @Column(name = "width")
    private Double width;
    @Column(name = "length")
    private Double length;
    @Column(name = "weight")
    private Double weight;
}
