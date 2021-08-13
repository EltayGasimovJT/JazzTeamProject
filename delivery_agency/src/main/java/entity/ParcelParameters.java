package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParcelParameters {
    private Double height;
    private Double width;
    private Double length;
    private Double weight;
}
