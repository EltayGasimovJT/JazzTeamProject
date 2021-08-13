package dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStateDto {
    private Long id;
    private String state;
}
