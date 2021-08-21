package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coefficientForPriceCalculation")
public class CoefficientForPriceCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parcelSizeLimit")
    private Integer parcelSizeLimit;
    @Column(name = "country")
    private String country;
    @Column(name = "countryCoefficient")
    private Double countryCoefficient;
}