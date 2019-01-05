package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

@SpringBootApplication
@RestController
public class UiController {

    @RequestMapping(Mappings.API_VERSION + Mappings.USER)
    public ResponseEntity user(Principal user) {

        if(user == null) {
            return MessageInfo.success(null, Arrays.asList("Please log in"));
        }
        return MessageInfo.success(user, Arrays.asList("You are logged in"));
    }

    @GetMapping("")
    public void callback(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4200/homepage");
    }
}
