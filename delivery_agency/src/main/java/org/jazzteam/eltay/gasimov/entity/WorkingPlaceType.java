package org.jazzteam.eltay.gasimov.entity;

public enum WorkingPlaceType {
    WAREHOUSE("WAREHOUSE"),
    PROCESSING_POINT("PROCESSING_POINT");

    private final String workingPlaceType;

    WorkingPlaceType(String workingPlaceType) {
        this.workingPlaceType = workingPlaceType;
    }

    @Override
    public String toString() {
        return this.workingPlaceType;
    }
}
