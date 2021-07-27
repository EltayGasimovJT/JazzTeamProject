package entity;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode @ToString
public class User {
    private long id;
    private String name;
    private String surname;
    private AbstractBuilding workingPlace;
    @Singular
    private List<String> roles;

    public User(long id, String name, String surname, AbstractBuilding workingPlace, List<String> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.workingPlace = workingPlace;
        this.roles = roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void addAllRoles(List<String> roles) {
        this.roles.addAll(roles);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public void removeAllRoles(List<String> roles) {
        this.roles.removeAll(roles);
    }
}
