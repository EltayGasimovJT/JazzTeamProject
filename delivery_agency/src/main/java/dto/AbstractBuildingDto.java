package dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AbstractBuildingDto extends AbstractLocationDto{
    private String location;
}
