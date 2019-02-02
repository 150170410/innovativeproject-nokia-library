package com.nokia.library.nokiainnovativeproject.config;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

import com.nokia.library.nokiainnovativeproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.*;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().cors().and()
                .authorizeRequests()
                .antMatchers("/error**", "/", "/login**",
						API_VERSION + BOOK_CATEGORY + GET_ALL).permitAll()
                .antMatchers(API_VERSION + BOOKS + GET_ONE,
                        API_VERSION + BOOKS + GET_ALL,
                        API_VERSION + BOOK_DETAILS + GET_ONE,
                        API_VERSION + BOOK_DETAILS + GET_ALL,
                        API_VERSION + EMAIL + CREATE,
                        API_VERSION + USER ,
                        API_VERSION + USER + CREATE,
                        API_VERSION + ADDRESS + GET_ALL).permitAll()
                .antMatchers(API_VERSION + BOOK_TO_ORDER + CREATE,
                        API_VERSION + BOOK_TO_ORDER + GET_ONE,
                        API_VERSION + BOOK_TO_ORDER + REMOVE,
                        API_VERSION + BOOK_TO_ORDER + UPDATE,
                        API_VERSION + BOOK_TO_ORDER + USER,
						API_VERSION + BOOK_TO_ORDER + GET_ALL,
                        API_VERSION + BOOK_TO_ORDER + SUBSCRIPTION,
                        API_VERSION + RENTALS + CREATE,
                        API_VERSION + RENTALS + GET_ALL,
                        API_VERSION + RENTALS + GET_ONE,
                        API_VERSION + RENTALS + HANDOVER,
                        API_VERSION + RENTALS + UPDATE,
						API_VERSION + RENTALS + REMOVE,
                        API_VERSION + RENTALS + RETURN,
                        API_VERSION + RENTALS + USER,
                        API_VERSION + BOOKS + RENTALS + GET_ONE,
                        API_VERSION + BOOKS + RESERVATIONS + GET_ONE,
                        API_VERSION + RESERVATIONS + CREATE,
                        API_VERSION + RESERVATIONS + GET_ALL,
                        API_VERSION + RESERVATIONS + GET_ONE,
						API_VERSION + RESERVATIONS + REMOVE,
						API_VERSION + RESERVATIONS + ACCEPT,
						API_VERSION + RESERVATIONS + CANCEL,
						API_VERSION + RESERVATIONS + REJECT,
                        API_VERSION + RESERVATIONS + USER,
                        API_VERSION + USER + UPDATE).hasAnyRole(EMPLOYEE, ADMIN)
                .antMatchers(API_VERSION + BOOK_AUTHOR + "/**",
                        API_VERSION + AUTOCOMPLETION + "/**",
                        API_VERSION + BOOK_CATEGORY + CREATE,
                        API_VERSION + BOOK_CATEGORY + UPDATE,
                        API_VERSION + BOOK_CATEGORY + REMOVE,
                        API_VERSION + BOOKS + CREATE,
                        API_VERSION + BOOKS + UPDATE,
                        API_VERSION + BOOKS + REMOVE,
                        API_VERSION + BOOKS + ASSIGN_ADMIN_TO_BOOKS,
                        API_VERSION + RENTALS + GET_ALL_FILL,
                        API_VERSION + RENTALS + GET_ONE_FILL,
                        API_VERSION + BOOK_DETAILS + CREATE,
                        API_VERSION + BOOK_DETAILS + UPDATE,
                        API_VERSION + BOOK_DETAILS + REMOVE,
                        API_VERSION + PICTURES + UPLOAD,
                        API_VERSION + USER + GET_ONE,
                        API_VERSION + USER + GET_ALL,
                        API_VERSION + USER + ASSIGN_ADMIN,
                        API_VERSION + USER + TAKE_ADMIN).hasRole(ADMIN)
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/callback").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }
}