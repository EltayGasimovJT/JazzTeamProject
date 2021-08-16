package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBuildingDto extends AbstractLocationDto {
    private String location;
    private WorkingPlaceType workingPlaceType;
}
