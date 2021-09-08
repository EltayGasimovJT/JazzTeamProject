package org.jazzteam.eltay.gasimov.controller.security;

import org.jazzteam.eltay.gasimov.entity.User;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User userEntity = userService.findByName(name);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(CustomModelMapper.mapUserToDto(userEntity));
    }
}