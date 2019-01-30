package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.services.EmailService;
import com.nokia.library.nokiainnovativeproject.utils.EmailRecipients;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.EMAIL)
public class EmailController {

    private final EmailService emailService;

    @PostMapping(Mappings.CREATE)
    public ResponseEntity sendEmail(@RequestBody @Valid Email email, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        emailService.sendSimpleMessage(email, EmailRecipients.recipients);
        return MessageInfo.success(null, Arrays.asList("Email" + Messages.get(SENT_SUCCESSFULLY)));
    }
}