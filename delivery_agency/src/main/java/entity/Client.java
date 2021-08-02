package entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Client {
    private Long id;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;
    private List<Order> orders;

}
