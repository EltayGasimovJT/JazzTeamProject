package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;
    private String name;
    private String surname;
    private String passportId;
    private String phoneNumber;
    private List<Order> orders;
}