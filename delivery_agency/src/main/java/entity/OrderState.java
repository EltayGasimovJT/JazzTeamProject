package entity;

import lombok.*;

import java.util.List;


@Data @Builder
public class OrderState {
    private Long id;
    private String state;
    private List<String> rolesAllowedWithdrawFromState;
    private List<String> rolesAllowedPutToState;
}
