package com.nokia.library.nokiainnovativeproject.config;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "login/**", "/error/**", "oauth/**").permitAll()
                .antMatchers(API_VERSION + BOOKS + GET_ONE,
                        API_VERSION + BOOKS + GET_ALL,
                        API_VERSION + BOOK_DETAILS + GET_ONE,
                        API_VERSION + BOOK_DETAILS + GET_ALL,
                        API_VERSION + EMAIL + CREATE).permitAll()
                .antMatchers(API_VERSION + BOOK_TO_ORDER + "/**",
                        API_VERSION + USER + GET).hasAnyRole("EMPLOYEE", "ADMIN")
                .antMatchers(API_VERSION + BOOK_AUTHOR + "/**",
                        API_VERSION + AUTOCOMPLETION + "/**",
                        API_VERSION + BOOK_CATEGORY + "/**",
                        API_VERSION + BOOKS + CREATE,
                        API_VERSION + BOOKS + UPDATE,
                        API_VERSION + BOOKS + REMOVE,
                        API_VERSION + BOOK_DETAILS + CREATE,
                        API_VERSION + BOOK_DETAILS + UPDATE,
                        API_VERSION + BOOK_DETAILS + REMOVE,
                        API_VERSION + PICTURES + UPLOAD,
                        API_VERSION + USER + TAKE_ADMIN,
                        API_VERSION + USER + ASSIGN_ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }
}