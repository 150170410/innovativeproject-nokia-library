package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.services.UserService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION)
public class UiController {

    private final UserService userService;

    @RequestMapping(Mappings.USER)
    public ResponseEntity user(Principal user) {

        if(user == null) {
            return MessageInfo.success(null, Arrays.asList("Please log in"));
        }
        return MessageInfo.success(user, Arrays.asList("You are logged in"));
    }

    @GetMapping(Mappings.USER + Mappings.GET_ALL)
    public ResponseEntity getAllUsers() {
        return MessageInfo.success(userService.getAllUsers(), Arrays.asList("List of users"));
    }

    @GetMapping(Mappings.USER + Mappings.GET_ONE)
    public ResponseEntity getUserById(@PathVariable Long id) {
        return MessageInfo.success(userService.getUserById(id), Arrays.asList("User of ID: " + id));
    }

    @PostMapping(Mappings.USER + Mappings.CREATE)
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(userService.createUser(userDTO), Arrays.asList("User created successfully"));
    }

    @PostMapping(Mappings.USER + Mappings.UPDATE)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(userService.updateUser(id, userDTO), Arrays.asList("User updated successfully"));
    }

    @PostMapping(Mappings.USER + Mappings.ASSIGN_ADMIN)
    public ResponseEntity assignAdminRoleToUser (@PathVariable Long id) {
        return MessageInfo.success(userService.assignAdminRoleToUser(id),
                Arrays.asList("The admin role has been successfully added"));
    }

    @PostMapping(Mappings.USER + Mappings.TAKE_ADMIN)
    public ResponseEntity takeAdminRoleFromUser(@PathVariable Long id) {
        return MessageInfo.success(userService.takeAdminRoleFromUser(id),
                Arrays.asList("The admin role has been successfully removed"));
    }

}
