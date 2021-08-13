package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientDto {
    private Long id;
    private String name;
    private String surname;
    private String passportId;
    private String phoneNumber;
    private List<OrderDto> orders;
}
