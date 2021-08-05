package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String passportID;
    private String phoneNumber;
}
