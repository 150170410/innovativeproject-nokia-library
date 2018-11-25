package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.EmailDTO;
import com.nokia.library.nokiainnovativeproject.services.EmailService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.EMAIL)
public class EmailController {

    private final EmailService emailService;

    @PostMapping(Mappings.CREATE)
    public MessageInfo sendEmail(@RequestBody @Valid EmailDTO emailDTO, BindingResult bindingResult) {
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : emailService.sendSimpleMessage(emailDTO);
    }
}