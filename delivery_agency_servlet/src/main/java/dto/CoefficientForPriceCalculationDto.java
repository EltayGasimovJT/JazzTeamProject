package dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculationDto {
    private Long id;
    private int parcelSizeLimit;
    private double countryCoefficient;
    private String country;
}