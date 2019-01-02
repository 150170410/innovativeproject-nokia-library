package com.nokia.library.nokiainnovativeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginCallbackController {

    @GetMapping("")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4200/homepage");
    }
}
