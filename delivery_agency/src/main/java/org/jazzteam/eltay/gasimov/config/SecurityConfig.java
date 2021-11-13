package org.jazzteam.eltay.gasimov.config;

import org.jazzteam.eltay.gasimov.controller.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    private static final int STRENGTH = 12;
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String PROCESSING_POINT_WORKER_ROLE = "PROCESSING_POINT_WORKER";
    private static final String WAREHOUSE_WORKER_ROLE = "WAREHOUSE_WORKER";
    private static final String START_PAGE_URL = "/";
    private static final String CREATE_ORDER_URL = "/createOrder";
    private static final String ORDERS_URL = "/orders/**";
    private static final String PROCESSING_POINT_WORKER_URL = "/processingPointActionPageRedirect/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(START_PAGE_URL).permitAll()
                .antMatchers(HttpMethod.POST, CREATE_ORDER_URL).hasAnyRole(ADMIN_ROLE, PROCESSING_POINT_WORKER_ROLE)
                .antMatchers(HttpMethod.POST, ORDERS_URL).hasRole(ADMIN_ROLE)
                .antMatchers(PROCESSING_POINT_WORKER_URL).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, ORDERS_URL).hasAnyRole(ADMIN_ROLE, PROCESSING_POINT_WORKER_ROLE)
                .antMatchers(HttpMethod.PUT, ORDERS_URL).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, ORDERS_URL).permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }
}
