package entity;

import java.util.List;

public class OrderState {
    private long id;
    private String state;
    private List<String> rolesAllowedWithdrawFromState;
    private List<String> rolesAllowedPutToState;
}
