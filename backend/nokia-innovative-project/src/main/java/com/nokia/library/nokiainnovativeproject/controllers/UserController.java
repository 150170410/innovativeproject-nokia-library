package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.services.UserService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.USER)
public class UserController {

    private final UserService userService;

    @GetMapping(Mappings.GET)
    public Principal user(Principal principal) {
        return principal;
    }

    @PostMapping(Mappings.ASSIGN_ADMIN)
    public ResponseEntity assignAdminRoleToUser (@PathVariable Long id) {
        return MessageInfo.success(userService.assignAdminRoleToUser(id),
                Arrays.asList("The admin role has been successfully added"));
    }

    @PostMapping(Mappings.TAKE_ADMIN)
    public ResponseEntity takeAdminRoleFromUser(@PathVariable Long id) {
        return MessageInfo.success(userService.takeAdminRoleFromUser(id),
                Arrays.asList("The admin role has been successfully removed"));
    }
}