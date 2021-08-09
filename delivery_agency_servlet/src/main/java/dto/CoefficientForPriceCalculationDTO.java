package dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculationDTO {
    private Long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
}