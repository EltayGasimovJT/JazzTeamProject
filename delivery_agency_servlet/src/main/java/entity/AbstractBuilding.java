package entity;

import lombok.Data;

@Data
public abstract class AbstractBuilding extends AbstractLocation {
    private String location;
}
