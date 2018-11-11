package com.nokia.library.nokiainnovativeproject.config;

import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

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
}