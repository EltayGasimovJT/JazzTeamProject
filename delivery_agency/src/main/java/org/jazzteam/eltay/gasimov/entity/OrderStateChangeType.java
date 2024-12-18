package org.jazzteam.eltay.gasimov.entity;

public enum OrderStateChangeType {
    READY_TO_SEND,
    FROM_ON_THE_WAY_TO_ACCEPTED,
    FROM_READY_TO_ON_THE_WAY,
    FROM_ON_THE_WAREHOUSE_TO_LOCKED,
    FROM_ON_PROCESSING_POINT_TO_LOCKED,
    FROM_ON_THE_WAREHOUSE_TO_THE_FINAL_WAREHOUSE,
    FROM_ON_TH_FINAL_WAREHOUSE_TO_THE_ON_THE_WAY_TO_THE_PROCESSING_POINT,
    FROM_READY_TO_STORE
}