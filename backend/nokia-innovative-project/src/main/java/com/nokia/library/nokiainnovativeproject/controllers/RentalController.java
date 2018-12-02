package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.services.RentalService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION)
public class RentalController {
    
    private final RentalService rentalService;

    @GetMapping(Mappings.RENTALS + Mappings.GET_ALL)
    public MessageInfo getAllRentals(){
        return MessageInfo.success(rentalService.getAllRentals(), Arrays.asList("Full list of book rentals"));
    }
    
    @GetMapping(Mappings.RENTALS + Mappings.GET_ONE)
    public MessageInfo getRentalById(@PathVariable Long id){
        return MessageInfo.success(rentalService.getRentalById(id), Arrays.asList("Rental with ID = " + id.toString()));
    }

    @GetMapping(Mappings.USERS + Mappings.GET_ONE + Mappings.RENTALS)
    public MessageInfo getRentalsByUserId(@PathVariable Long userId){
        return MessageInfo.success(rentalService.getRentalsByUserId(userId), Arrays.asList("Rentals with UserID = " + userId.toString()));
    }

    @GetMapping(Mappings.BOOKS + Mappings.GET_ONE + Mappings.RENTALS)
    public MessageInfo getRentalsByBookId(@PathVariable Long bookId){
        return MessageInfo.success(rentalService.getRentalsByBookId(bookId), Arrays.asList("Rentals with BookID = " + bookId.toString()));
    }
    
    @PostMapping(Mappings.RENTALS + Mappings.CREATE)
    public MessageInfo createRental(@RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult){
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : MessageInfo.success(rentalService.createRental(rentalDTO), Arrays.asList("Rental created successfully"));
    }

    @PostMapping(Mappings.RENTALS + Mappings.UPDATE)
    public MessageInfo updateRental(@PathVariable Long id, @RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult){
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : MessageInfo.success(rentalService.updateRental(id, rentalDTO), Arrays.asList("Rental updated successfully"));
    }

    @DeleteMapping(Mappings.RESERVATIONS + Mappings.REMOVE)
    public MessageInfo deleteRental(@PathVariable Long id){
        rentalService.deleteRental(id);
        return MessageInfo.success(null, Arrays.asList("Rental with ID = " + id.toString() + " removed successfully"));
    }
}
