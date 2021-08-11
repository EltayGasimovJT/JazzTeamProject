package dto;

import entity.AbstractBuilding;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private AbstractBuilding workingPlace;
    private List<String> roles;
}
