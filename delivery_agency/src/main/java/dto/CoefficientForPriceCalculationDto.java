package dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculationDto {
    private Long id;
    private int parcelSizeLimit;
    private String country;
    private double countryCoefficient;
}