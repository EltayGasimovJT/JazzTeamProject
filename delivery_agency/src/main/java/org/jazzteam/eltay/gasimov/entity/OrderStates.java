package org.jazzteam.eltay.gasimov.entity;


public enum OrderStates {
    READY_TO_SEND("Готов к отправке"),
    ON_THE_WAY_TO_THE_WAREHOUSE("Заказ на пути к конечному промежуточному складу"),
    ON_THE_WAREHOUSE("On the warehouse"),
    ON_THE_WAY_TO_THE_FINAL_WAREHOUSE("Заказ на пути на промежуточный склад"),
    ON_THE_FINAL_WAREHOUSE("Заказ на конечном промежуточном складе"),
    ON_THE_WAY_TO_THE_RECEPTION("Заказ на пути к пунтку выдачи"),
    ORDER_COMPLETED("Order canceled"),
    ORDER_LOCKED("Заказ заблокирован");

    private String state;

    OrderStates(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }
}
