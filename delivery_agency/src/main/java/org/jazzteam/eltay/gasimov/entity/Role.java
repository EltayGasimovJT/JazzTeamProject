package org.jazzteam.eltay.gasimov.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Role {
    ADMIN(Stream.of(Permission.ORDER_CREATE).collect(Collectors.toCollection(HashSet::new)))
    ,PROCESSING_POINT_WORKER(Stream.of(Permission.ORDER_CREATE).collect(Collectors.toCollection(HashSet::new)));

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
