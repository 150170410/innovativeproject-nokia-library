package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.services.RentalService;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.nokia.library.nokiainnovativeproject.validators.BindingResultsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION)
public class RentalController {
    
    private final RentalService rentalService;

    @GetMapping(RENTALS + GET_ALL)
    public MessageInfo getAllRentals(){
        return MessageInfo.success(rentalService.getAllRentals(), Arrays.asList("Full list of book rentals"));
    }
    
    @GetMapping(RENTALS + GET_ONE)
    public MessageInfo getRentalById(@PathVariable Long id){
        return MessageInfo.success(rentalService.getRentalById(id), Arrays.asList("Rental with ID = " + id.toString()));
    }

    @GetMapping(USERS + GET_ONE + RENTALS)
    public MessageInfo getRentalsByUserId(@PathVariable Long userId){
        return MessageInfo.success(rentalService.getRentalsByUserId(userId), Arrays.asList("Rentals with UserID = " + userId.toString()));
    }

    @GetMapping(BOOKS + GET_ONE + RENTALS)
    public MessageInfo getRentalsByBookId(@PathVariable Long bookId){
        return MessageInfo.success(rentalService.getRentalsByBookId(bookId), Arrays.asList("Rentals with BookID = " + bookId.toString()));
    }
    
    @PostMapping(RENTALS + CREATE)
    public MessageInfo createRental(@RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, rentalDTO.getClass().getSimpleName());
        return MessageInfo.success(rentalService.createRental(rentalDTO), Arrays.asList("Rental created successfully"));
    }

    @PostMapping(RENTALS + UPDATE)
    public MessageInfo updateRental(@PathVariable Long id, @RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, rentalDTO.getClass().getSimpleName());
        return MessageInfo.success(rentalService.updateRental(id), Arrays.asList("Rental updated successfully"));
    }

    @DeleteMapping(RENTALS + REMOVE)
    public MessageInfo deleteRental(@PathVariable Long id){
        rentalService.deleteRental(id);
        return MessageInfo.success(null, Arrays.asList("Rental with ID = " + id.toString() + " removed successfully"));
    }
}
