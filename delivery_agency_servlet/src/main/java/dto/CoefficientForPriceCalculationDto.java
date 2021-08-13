package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoefficientForPriceCalculationDto {
    private Long id;
    private Integer parcelSizeLimit;
    private Double countryCoefficient;
    private String country;
}