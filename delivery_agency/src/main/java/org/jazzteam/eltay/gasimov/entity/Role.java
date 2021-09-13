package org.jazzteam.eltay.gasimov.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Role {
    ROLE_ADMIN(Stream.of(Permission.ORDER_CREATE, Permission.ADD_USER).collect(Collectors.toCollection(HashSet::new)))
    , ROLE_PROCESSING_POINT_WORKER(Stream.of(Permission.ORDER_CREATE).collect(Collectors.toCollection(HashSet::new))),
    ROLE_WAREHOUSE_WORKER(Stream.of(Permission.SEND_ORDERS).collect(Collectors.toCollection(HashSet::new)));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
