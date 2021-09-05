package org.jazzteam.eltay.gasimov.entity;


public enum OrderStates {
    READY_TO_SEND("Ready to send"),
    ON_THE_WAY_TO_THE_WAREHOUSE("On the way to the final warehouse"),
    ON_THE_WAREHOUSE("On the warehouse"),
    ON_THE_WAY_TO_THE_FINAL_WAREHOUSE("On the way to the final warehouse"),
    ON_THE_FINAL_WAREHOUSE("On the final warehouse"),
    ON_THE_WAY_TO_THE_RECEPTION("On the way to the reception"),
    ORDER_COMPLETED("Order canceled"),
    ORDER_LOCKED("Order locked");

    private String state;

    OrderStates(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }
}
