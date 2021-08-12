package entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class User {
    private Long id;
    private String name;
    private String surname;
    private AbstractBuilding workingPlace;
    private List<String> roles;
}