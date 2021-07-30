package entity;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class PriceCalculationRule {
    private long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
    private double initialParcelPrice;
}
