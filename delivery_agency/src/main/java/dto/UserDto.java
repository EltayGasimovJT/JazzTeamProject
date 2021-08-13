package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private AbstractBuildingDto workingPlace;
    private List<String> roles;
}
