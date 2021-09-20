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
@Table(name = "coefficient_for_price_calculation")
public class CoefficientForPriceCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parcel_size_limit")
    private Integer parcelSizeLimit;
    @Column(name = "country")
    private String country;
    @Column(name = "country_coefficient")
    private Double countryCoefficient;
}