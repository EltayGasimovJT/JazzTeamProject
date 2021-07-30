package entity;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode @ToString @AllArgsConstructor
public class Client {
    private long id;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;
    private List<Order> orders;

}
