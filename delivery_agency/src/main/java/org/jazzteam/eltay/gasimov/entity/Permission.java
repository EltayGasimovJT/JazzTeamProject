package org.jazzteam.eltay.gasimov.entity;

import lombok.Getter;

@Getter
public enum Permission {
    ORDER_CREATE("orders:create"),
    ADD_USER("users:add");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
