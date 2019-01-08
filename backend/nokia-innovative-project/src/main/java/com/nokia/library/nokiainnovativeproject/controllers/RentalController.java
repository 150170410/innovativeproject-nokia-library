package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.services.RentalService;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getAllRentals(){
        return MessageInfo.success(rentalService.getAllRentals(), Arrays.asList("Full list of book rentals"));
    }
    
    @GetMapping(RENTALS + GET_ONE)
    public ResponseEntity getRentalById(@PathVariable Long id){
        return MessageInfo.success(rentalService.getRentalById(id), Arrays.asList("Rental with ID = " + id.toString()));
    }

    @GetMapping(USERS + RENTALS + GET_ONE)
    public ResponseEntity getRentalsByUserId(@PathVariable Long id){
        return MessageInfo.success(rentalService.getRentalsByUserId(id), Arrays.asList("Rentals with UserID = " + id.toString()));
    }

    @GetMapping(BOOKS + RENTALS + GET_ONE)
    public ResponseEntity getRentalsByBookId(@PathVariable Long id){
        return MessageInfo.success(rentalService.getRentalsByBookId(id), Arrays.asList("Rentals with BookID = " + id.toString()));
    }
    
    @PostMapping(RENTALS + CREATE)
    public ResponseEntity createRental(@RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(rentalService.createRental(rentalDTO), Arrays.asList("Rental created successfully"));
    }

    @PostMapping(RENTALS + UPDATE)
    public ResponseEntity prolongRental(@PathVariable Long id){
        return MessageInfo.success(rentalService.updateRental(id), Arrays.asList("Rental updated successfully"));
    }

    @PostMapping(RENTALS + HANDOVER)
    public ResponseEntity handOverRental(@PathVariable Long id){
        return MessageInfo.success(rentalService.handOverRental(id), Arrays.asList("Rental updated successfully"));
    }

    @DeleteMapping(RENTALS + REMOVE)
    public ResponseEntity deleteRental(@PathVariable Long id){
        rentalService.deleteRental(id);
        return MessageInfo.success(null, Arrays.asList("Rental with ID = " + id.toString() + " removed successfully"));
    }
}
