package entity;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter @NoArgsConstructor
public class OrderState {
    private long id;
    private String state;
    @Singular("rolesAllowedWithdrawFromState")
    private List<String> rolesAllowedWithdrawFromState;
    @Singular("rolesAllowedPutToState")
    private List<String> rolesAllowedPutToState;
}
