package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderState {
    private Long id;
    private String state;
    private List<String> rolesAllowedWithdrawFromState;
    private List<String> rolesAllowedPutToState;
}