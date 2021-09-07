package org.jazzteam.eltay.gasimov.controller.security;

import lombok.NoArgsConstructor;
import org.jazzteam.eltay.gasimov.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private boolean isActive;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(UserDto userEntity) {
        CustomUserDetails c = new CustomUserDetails();
        c.login = userEntity.getName();
        c.password = userEntity.getPassword();
        c.grantedAuthorities = userEntity.getRole().getAuthorities();
        c.isActive = true;
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    public CustomUserDetails(String login, String password, Collection<? extends GrantedAuthority> grantedAuthorities, boolean isActive) {
        this.login = login;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.isActive = isActive;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    public static UserDetails getUserDetailsFromUser(UserDto userDto){
        return new User(
                userDto.getName(),
                userDto.getPassword(),
                userDto.getRole().getAuthorities()
        );
    }
}
