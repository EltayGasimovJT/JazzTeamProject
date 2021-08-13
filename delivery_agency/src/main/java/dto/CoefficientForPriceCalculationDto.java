package dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoefficientForPriceCalculationDto {
    private Long id;
    private Integer parcelSizeLimit;
    private String country;
    private Double countryCoefficient;
}