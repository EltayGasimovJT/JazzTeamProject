package entity;

import java.util.List;

public class User {
    private long id;
    private String name;
    private String surname;
    private List<String> roles;

    public User(long id, String name, String surname, List<String> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
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
