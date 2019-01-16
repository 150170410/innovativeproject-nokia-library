package com.nokia.library.nokiainnovativeproject.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CallbackController {

    @Value("${fe_url}")
    private String feUrl;

    @GetMapping("/callback")
    void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(feUrl + "/homepage");
    }
}
