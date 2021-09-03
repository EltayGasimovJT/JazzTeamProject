package org.jazzteam.eltay.gasimov.controller.constants;

public class ControllerConstant {
    private ControllerConstant() {
    }

    public static final String CLIENTS_URL = "/clients";
    public static final String CLIENTS_BY_PASSPORT_URL = "/clients/byPassport";
    public static final String CLIENTS_BY_PASSPORT_PATH_VARIABLE_URL = CLIENTS_URL + "/ordersByPhoneNumber/{phoneNumber}";
    public static final String CLIENTS_BY_ID_URL = CLIENTS_URL + "/{id}";
    public static final String CLIENTS_BY_PHONE_NUMBER_URL = CLIENTS_URL + "/findByPhoneNumber";
    public static final String FIND_CODE_URL = "/codes/findCode";
    public static final String COEFFICIENTS_URL = "/coefficients";
    public static final String COEFFICIENTS_BY_ID_URL = COEFFICIENTS_URL + "/{id}";
    public static final String ORDERS_URL = "/orders";
    public static final String ORDERS_BY_ID_URL = ORDERS_URL + "{id}";
    public static final String ORDERS_CREATE_ORDER_URL = ORDERS_URL + "/createOrder";
    public static final String ORDERS_FIND_BY_SENDER_PASSPORT_URL = ORDERS_URL + "/findBySenderPassport";
}
