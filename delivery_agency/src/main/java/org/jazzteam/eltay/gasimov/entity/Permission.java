package org.jazzteam.eltay.gasimov.entity;

import lombok.Getter;

@Getter
public enum Permission {
    ORDER_CREATE("orders:create");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
