package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelParametersDto {
    @Positive(message = "Height must be not zero and must be positive")
    @NotEmpty(message = "Height must have value")
    private Double height;
    @Positive(message = "Width must be not zero and must be positive")
    @NotEmpty(message = "Width must have value")
    private Double width;
    @Positive(message = "Length must be not zero and must be positive")
    @NotEmpty(message = "Length must have value")
    private Double length;
    @Positive(message = "Weight must be not zero and must be positive")
    @NotEmpty(message = "Weight must have value")
    private Double weight;
}
