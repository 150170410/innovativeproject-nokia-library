package com.nokia.library.nokiainnovativeproject.config;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "login/**", "/error/**", "oauth/**").permitAll()
                .antMatchers("/api/v1/bookDetails/getAll").hasRole("EMPLOYEE")
                .antMatchers("/api/v1/books/getAll").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}