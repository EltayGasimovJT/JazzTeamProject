package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParcelParametersDto {
    private Double height;
    private Double width;
    private Double length;
    private Double weight;
}
