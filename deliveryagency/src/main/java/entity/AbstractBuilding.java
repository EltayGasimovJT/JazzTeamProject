package entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public abstract class AbstractBuilding extends AbstractLocation {
    private String location;
}
