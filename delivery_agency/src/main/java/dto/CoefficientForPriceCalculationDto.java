package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientForPriceCalculationDto {
    private Long id;
    private Integer parcelSizeLimit;
    private String country;
    private Double countryCoefficient;
}