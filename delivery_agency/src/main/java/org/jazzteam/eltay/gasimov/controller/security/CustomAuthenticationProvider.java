package org.jazzteam.eltay.gasimov.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component(value = "customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userService == null)
            throw new InternalAuthenticationServiceException("WorkerService is null");

        UserDetails user = userService.loadUserByUsername(login);
        if (user == null)
            throw new AuthenticationCredentialsNotFoundException("Not found");

        if (user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
        } else {
            throw new AuthenticationServiceException("Unable to auth against third party systems");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
