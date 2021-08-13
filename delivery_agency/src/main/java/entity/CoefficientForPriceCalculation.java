package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculation {
    private Long id;
    private Integer parcelSizeLimit;
    private String country;
    private Double countryCoefficient;
}