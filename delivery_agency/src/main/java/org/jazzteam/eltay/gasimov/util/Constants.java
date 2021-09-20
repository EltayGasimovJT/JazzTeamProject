package org.jazzteam.eltay.gasimov.util;

public class Constants {

    private Constants(){

    }


    public static final int INITIAL_WEIGHT = 50000;
    public static final double PRICE_FOR_PACKAGE = 1.9;
    public static final double COEFFICIENT_FOR_KILOGRAMS_CONVERT = 1000;
    public static final double PRICE_FOR_DELIVERY = 3.25;
    public static final double WEIGHT_COEFFICIENT = 0.5;
    public static final double VOLUME_COEFFICIENT = 0.10;
    public static final double INITIAL_COUNTRY_COEFFICIENT = 0.8;
    public static final double COEFFICIENT_FOR_VOLUME_CALCULATION = 1000000000;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;
    public static final String ORDER_WAS_SENT = "Заказ с номером # ";
    public static final String ID = "/{id}";
    public static final String CLIENTS_URL = "/clients";
    public static final String CLIENTS_BY_PASSPORT_URL = "/clients/byPassport";
    public static final String CLIENTS_BY_PASSPORT_PATH_VARIABLE_URL = CLIENTS_URL + "/ordersByPhoneNumber/{phoneNumber}";
    public static final String CLIENTS_BY_ID_URL = CLIENTS_URL + ID;
    public static final String CLIENTS_BY_PHONE_NUMBER_URL = CLIENTS_URL + "/findByPhoneNumber";
    public static final String GET_GENERATED_CODE_URL = CLIENTS_URL + "/getCode";
    public static final String FIND_CODE_URL = "/codes/findCode";
    public static final String COEFFICIENTS_URL = "/coefficients";
    public static final String COEFFICIENTS_BY_ID_URL = COEFFICIENTS_URL + ID;
    public static final String CALCULATE_PRICE_URL = "/calculatePrice/{country}";
    public static final String ORDERS_URL = "/orders";
    public static final String ORDERS_BY_ID_URL = ORDERS_URL + ID;
    public static final String ORDERS_CHANGE_ORDER_STATE_URL = "/changeOrderState";
    public static final String ORDERS_CREATE_ORDER_URL = "/createOrder";
    public static final String ORDERS_FIND_BY_SENDER_PASSPORT_URL = ORDERS_URL + "/findBySenderPassport";
    public static final String ORDERS_BY_TRACK_NUMBER = ORDERS_URL + "/deleteByTrackNumber/{orderNumber}";
    public static final String ORDERS_FIND_BY_TRACK_NUMBER_URL = ORDERS_URL + "/findByTrackNumber";
    public static final String ORDERS_FIND_HISTORY_URL = ORDERS_URL + "/findHistory" + ID;
    public static final String PROCESSING_POINTS_URL = "/processingPoints";
    public static final String PROCESSING_POINTS_BY_ID_URL = PROCESSING_POINTS_URL + ID;
    public static final String ORDER_STATES_URL = "/orderStates";
    public static final String ORDER_STATES_BY_ID_URL = ORDER_STATES_URL + ID;
    public static final String AUTHORIZATION_URL = "/auth";
    public static final String REGISTRATION_URL = "/register";
    public static final String WORKERS_URL = "/users";
    public static final String WORKERS_BY_ID_URL = WORKERS_URL + ID;
    public static final String WORKERS_CHANGE_WORKING_PLACE_URL = WORKERS_URL + "/changeWorkingPlace/{id}";
    public static final String WORKERS_FIND_ROLES_URL = WORKERS_URL + "/findRoles";
    public static final String WORKERS_GET_STATES_URL = WORKERS_URL + "/getStatesByRole";
    public static final String WORKERS_GET_CURRENT_WORKER_URL = WORKERS_URL + "/getCurrentWorker";
    public static final String WORKER_ROLES_URL = "/userRoles";
    public static final String WORKER_ROLES_BY_ID_URL = WORKER_ROLES_URL + ID;
    public static final String VOYAGES_URL = "/voyages";
    public static final String VOYAGES_BY_ID_URL = VOYAGES_URL + ID;
    public static final String WAREHOUSES_URL = "/warehouses";
    public static final String WAREHOUSES_BY_ID_URL = WAREHOUSES_URL + ID;
    public static final String CODE_FIND_URL = "/codes/findCode";

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    public static final String ROLE_PICKUP_WORKER = "Pick up Worker";
    public static final String READY_TO_SEND = "Готов к отправке";
    public static final String WAREHOUSE_NOT_ALLOWED_STATE_CHANGING_MESSAGE = "Вданный момент вы не можете установить статус для этого заказа";

}
