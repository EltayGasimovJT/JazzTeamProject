package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoefficientForPriceCalculation {
    private Long id;
    private Integer parcelSizeLimit;
    private String country;
    private Double countryCoefficient;
}