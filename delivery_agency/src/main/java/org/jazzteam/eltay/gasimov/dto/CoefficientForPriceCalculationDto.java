package org.jazzteam.eltay.gasimov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientForPriceCalculationDto {
    private Long id;
    @PositiveOrZero(message = "Parcel size limit must be positive")
    @NotEmpty(message = "Parcel size limit must have value")
    private Integer parcelSizeLimit;
    @NotBlank(message = "Country cannot be empty")
    @Size(min = 1, max = 50, message = "Country size must be between 1 and 50 characters")
    private String country;
    @Positive(message = "Country coefficient must be positive")
    @NotEmpty(message = "Country coefficient must have value")
    private Double countryCoefficient;
}