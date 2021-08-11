package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoefficientForPriceCalculationDto {
    private Long id;
    private int parcelSizeLimit;
    private double countryCoefficient;
    private String country;
}