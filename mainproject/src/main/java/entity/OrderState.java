package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderState {
    private long id;
    private String state;
    @Singular("rolesAllowedWithdrawFromState")
    private List<String> rolesAllowedWithdrawFromState;
    @Singular("rolesAllowedPutToState")
    private List<String> rolesAllowedPutToState;
}
