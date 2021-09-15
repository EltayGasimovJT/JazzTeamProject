package org.jazzteam.eltay.gasimov.entity;


public enum OrderStates {
    READY_TO_SEND("Готов к отправке"),
    ON_THE_PROCESSING_POINT_STORAGE("Заказ на складе отправки"),
    ON_THE_WAY_TO_THE_WAREHOUSE("Заказ в пути к промежуточному складу"),
    ON_THE_WAREHOUSE("Заказ на промежуточном складе"),
    ON_THE_WAY_TO_THE_FINAL_WAREHOUSE("Заказ на пути на промежуточный склад"),
    ON_THE_FINAL_WAREHOUSE("Заказ на конечном промежуточном складе"),
    ON_THE_WAY_TO_THE_RECEPTION("Заказ в пути к пункту выдачи"),
    ON_THE_RECEPTION_STORE("Заказ находится на складе пункта выдачи"),
    READY_TO_TAKE("Заказ готов к выдаче"),
    ORDER_LOCKED("Заказ заблокирован"),
    ORDER_COMPLETE("Заказ выдан клиенту");

    private final String state;

    OrderStates(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }
}
