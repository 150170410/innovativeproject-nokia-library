package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@SpringBootApplication
@RestController
public class UiController {

    @RequestMapping(Mappings.USER)
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("")
    public void callback(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4200/homepage");
    }
}
