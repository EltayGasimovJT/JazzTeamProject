package entity;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParcelParameters {
    private double height;
    private double width;
    private double length;
    private double weight;
}
