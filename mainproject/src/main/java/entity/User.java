package entity;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode @ToString @AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private AbstractBuilding workingPlace;
    private List<String> roles;
}
