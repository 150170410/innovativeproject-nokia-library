package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.services.AddressService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(Mappings.API_VERSION + Mappings.ADDRESS)
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping(Mappings.GET_ALL)
    public ResponseEntity getAllAddresses() {
        return MessageInfo.success(addressService.getAllAddress(), Arrays.asList(Messages.get(LIST_OF) + "addresses"));
    }
}
