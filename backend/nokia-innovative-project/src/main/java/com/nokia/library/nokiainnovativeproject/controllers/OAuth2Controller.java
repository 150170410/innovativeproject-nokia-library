package com.nokia.library.nokiainnovativeproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/OAuth2")
public class OAuth2Controller {

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
