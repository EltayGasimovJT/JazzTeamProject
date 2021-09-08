package org.jazzteam.eltay.gasimov.config;

import org.jazzteam.eltay.gasimov.controller.security.CustomAuthenticationProvider;
import org.jazzteam.eltay.gasimov.controller.security.JwtFilter;
import org.jazzteam.eltay.gasimov.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    private static final int STRENGTH = 12;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/createOrder").hasAuthority(Permission.ORDER_CREATE.getPermission())
                //.antMatchers("/createOrder.html").hasAuthority(Permission.ORDER_CREATE.getPermission())
                //.antMatchers("/processingPointWorkerActionPage.html").hasAuthority(Permission.ORDER_CREATE.getPermission())
                .antMatchers("/homePage.html").permitAll()
                .antMatchers("/clientsOrders.html").permitAll()
                .antMatchers("/orderInfo.html").permitAll()
                .antMatchers("/ticketPage.html").permitAll()
                .antMatchers("/trackOrder.html").permitAll()
                .and()
                //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //.formLogin()
                //.defaultSuccessUrl("/homePage.html");
                //.and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
