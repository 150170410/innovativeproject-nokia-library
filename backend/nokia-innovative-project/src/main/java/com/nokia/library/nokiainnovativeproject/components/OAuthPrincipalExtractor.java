package com.nokia.library.nokiainnovativeproject.components;

import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthPrincipalExtractor implements PrincipalExtractor {

    private final UserService userService;

    @Override
    public Object extractPrincipal(Map<String, Object> map) {

        String email = (String)map.get("email");
        User user = userService.findUserByEmail(email);

        if(user == null) {
            user = new User();

            user.setFirstName("null");
            user.setLastName("null");
            String userName = (String)map.get("name");
            String[] splitResult = userName.split(" ");
            if(splitResult != null && splitResult.length > 0) {
                user.setFirstName(splitResult[0]);
                if(splitResult.length > 1) {
                    user.setLastName(splitResult[1]);
                }
            }

            user.setEmail(email);

            Role role = new Role();
            role.setRole("ROLE_EMPLOYEE");
            user.setRoles(Arrays.asList(role));

            user = userService.createUser(user);
        }
        return user;
    }
}