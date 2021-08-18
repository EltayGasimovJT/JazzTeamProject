package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelParametersDto {
    private Double height;
    private Double width;
    private Double length;
    private Double weight;
}
