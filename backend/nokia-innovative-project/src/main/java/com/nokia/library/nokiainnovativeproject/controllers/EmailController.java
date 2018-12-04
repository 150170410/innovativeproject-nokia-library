package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.services.EmailService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.EMAIL)
public class EmailController {

    private final EmailService emailService;

    @PostMapping(Mappings.CREATE)
    public ResponseEntity sendEmail(@RequestBody @Valid Email email, BindingResult bindingResult) {
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        if (errors != null) return ResponseEntity.badRequest().body(errors);
        try{
            emailService.sendSimpleMessage(email);
        }
        catch(MailException e){
            return ResponseEntity.ok().body(new MessageInfo(false, e.toString(), Arrays.asList("Failed to send messages")));
        }
        return ResponseEntity.ok().body(new MessageInfo(true, "success", Arrays.asList("Email sent successfully")));
    }
}