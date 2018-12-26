package com.nokia.library.nokiainnovativeproject.components;

import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthAuthoritiesExtractor implements AuthoritiesExtractor {

    private final UserService userService;

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {

        String email = (String) map.get("email");
        User user = userService.findUserByEmail(email);

        List<Role> roles = user.getRoles();
        if(roles != null) {
            List<String> userRoles = new ArrayList<>();
            for(Role role: roles) {
                userRoles.add(role.getRole());
            }
            return AuthorityUtils.createAuthorityList(userRoles.stream().toArray(size ->new String[size]));
        }
        return Collections.emptyList();
    }
}