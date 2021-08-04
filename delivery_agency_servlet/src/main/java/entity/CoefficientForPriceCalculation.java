package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculation {
    private Long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
}