package com.nokia.library.nokiainnovativeproject.config;

import com.nokia.library.nokiainnovativeproject.services.UserService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.     //antMatcher("/").authorizeRequests().anyRequest();
            httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS + "/**")
                .hasRole("EMPLOYEE")
                .and()
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}