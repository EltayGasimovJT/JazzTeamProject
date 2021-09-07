package org.jazzteam.eltay.gasimov.controller.security;

import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User userEntity = userService.findByName(name);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}