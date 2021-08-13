package dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractBuildingDto extends AbstractLocationDto {
    private String location;
}
