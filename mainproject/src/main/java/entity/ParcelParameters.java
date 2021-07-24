package entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ParcelParameters {
    private double height;
    private double width;
    private double length;
    private double weight;

    public ParcelParameters(double height, double width, double length, double weight) {
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }
}
