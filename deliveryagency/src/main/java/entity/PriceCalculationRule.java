package entity;

import lombok.*;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class PriceCalculationRule {
    private long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
}
