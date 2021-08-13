package dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbstractBuildingDto {
    private String location;
}
