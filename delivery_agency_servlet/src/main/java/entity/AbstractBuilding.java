package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBuilding extends AbstractLocation {
    private String location;
    private WorkingPlaceType workingPlaceType;
}