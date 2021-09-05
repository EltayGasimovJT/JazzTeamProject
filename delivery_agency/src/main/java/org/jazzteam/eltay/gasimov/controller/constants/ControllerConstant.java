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
    public static final String ORDERS_FIND_BY_TRACK_NUMBER_URL = ORDERS_URL + "/findByTrackNumber";
    public static final String ORDERS_FIND_HISTORY_URL = ORDERS_URL + "/findHistory/{id}";
    public static final String PROCESSING_POINTS_URL = "/processingPoints";
    public static final String PROCESSING_POINTS_BY_ID_URL = PROCESSING_POINTS_URL + "/{id}";
    public static final String ORDER_STATES_URL = "/orderStates";
    public static final String ORDER_STATES_BY_ID_URL = ORDER_STATES_URL + "/{id}";
    public static final String AUTHORIZATION_URL = "/auth";
    public static final String REGISTRATION_URL = "/register";
    public static final String USERS_URL = "/users";
    public static final String USERS_BY_ID_URL = USERS_URL + "/{id}";
    public static final String USERS_CHANGE_WORKING_PLACE_URL = USERS_URL + "/changeWorkingPlace/{id}";
    public static final String USER_ROLES_URL = "/userRoles";
    public static final String USER_ROLES_BY_ID_URL = USER_ROLES_URL + "/{id}";
    public static final String VOYAGES_URL = "/voyages";
    public static final String VOYAGES_BY_ID_URL = VOYAGES_URL + "/{id}";
    public static final String WAREHOUSES_URL = "/warehouses";
    public static final String WAREHOUSES_BY_ID_URL = WAREHOUSES_URL + "/{id}";
}
