package entity;

import lombok.*;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class CoefficientForPrice {
    private Long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
}