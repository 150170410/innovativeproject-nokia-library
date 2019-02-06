package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.services.UserService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.*;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION)
public class UiController {

    private final UserService userService;

    @RequestMapping(Mappings.USER)
    public ResponseEntity user(Principal user) {

        if(user == null) {
            return MessageInfo.success(null,
                    Arrays.asList(Messages.get(USER_NOT_LOGGED_IN)));
        }
        return MessageInfo.success(user,
                Arrays.asList(Messages.get(USER_LOGGED_IN)));
    }

    @GetMapping(Mappings.USER + Mappings.GET_ALL)
    public ResponseEntity getAllUsers() {
        return MessageInfo.success(userService.getAllUsers(),
                Arrays.asList(Messages.get(LIST_OF) + "users."));
    }

    @GetMapping(Mappings.USER + Mappings.GET_ONE)
    public ResponseEntity getUserById(@PathVariable Long id) {
        return MessageInfo.success(userService.getUserById(id),
                Arrays.asList("User" + Messages.get(REQUESTED)));
    }

    @PostMapping(Mappings.USER + Mappings.CREATE)
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO userDTO,
                                       BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(userService.createUser(userDTO),
                Arrays.asList("User" + Messages.get(CREATED_SUCCESSFULLY)));
    }

    @PostMapping(Mappings.USER + Mappings.UPDATE)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody @Valid
            UserDTO userDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(userService.updateUser(id, userDTO),
                Arrays.asList("User" + Messages.get(UPDATED_SUCCESSFULLY)));
    }

    @Secured(ROLE_ADMIN)
    @PostMapping(Mappings.USER + Mappings.ASSIGN_ADMIN)
    public ResponseEntity assignAdminRoleToUser (@PathVariable Long id) {
        return MessageInfo.success(userService.assignAdminRoleToUser(id),
                Arrays.asList(Messages.get(ADMIN_ROLE_ADDED)));
    }

    @Secured(ROLE_ADMIN)
    @PostMapping(Mappings.USER + Mappings.TAKE_ADMIN)
    public ResponseEntity takeAdminRoleFromUser(@PathVariable Long id) {
        return MessageInfo.success(userService.takeAdminRoleFromUser(id),
                Arrays.asList(Messages.get(ADMIN_ROLE_REMOVED)));
    }

    @Secured(ROLE_ADMIN)
    @PostMapping(Mappings.USER + Mappings.LOCK_ACCOUNT)
    public ResponseEntity lockUserAccount(@PathVariable Long id) {
        return MessageInfo.success(userService.lockUserAccount(id),
                Arrays.asList(Messages.get(USER_ACCOUNT_LOCKED)));
    }

    @Secured(ROLE_ADMIN)
    @PostMapping(Mappings.USER + Mappings.UNLOCK_ACCOUNT)
    public ResponseEntity unlockUserAccount(@PathVariable Long id) {
        return MessageInfo.success(userService.unlockUserAccount(id),
                Arrays.asList(Messages.get(USER_ACCOUNT_UNLOCKED)));
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(Mappings.USER + Mappings.ADMINS + Mappings.GET_ALL)
    public ResponseEntity getAdmins() {
        return MessageInfo.success(userService.getAllAdmins(),
                Arrays.asList(Messages.get(LIST_OF) + "admins."));
    }
}