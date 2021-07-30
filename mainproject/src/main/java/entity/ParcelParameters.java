package entity;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class ParcelParameters {
    private double height;
    private double width;
    private double length;
    private double weight;
}
