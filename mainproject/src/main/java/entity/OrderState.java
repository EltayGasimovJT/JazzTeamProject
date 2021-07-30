package entity;

import lombok.*;

import java.util.List;


@Getter
@Setter @NoArgsConstructor
@AllArgsConstructor
public class OrderState {
    private long id;
    private String state;
    private List<String> rolesAllowedWithdrawFromState;
    private List<String> rolesAllowedPutToState;
}
