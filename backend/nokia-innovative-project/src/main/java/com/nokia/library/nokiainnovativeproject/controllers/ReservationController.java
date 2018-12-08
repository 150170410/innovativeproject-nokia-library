package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.services.ReservationService;
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
@RequestMapping(API_VERSION )
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping(RESERVATIONS + GET_ALL)
    public MessageInfo getAllReservations(){
        return MessageInfo.success(reservationService.getAllReservations(), Arrays.asList("Full list of book reservations"));
    }

    @GetMapping(RESERVATIONS + GET_ONE)
    public MessageInfo getReservationById(@PathVariable Long id){
        return MessageInfo.success(reservationService.getReservationById(id), Arrays.asList("Reservation with ID = " + id.toString()));
    }

    @GetMapping(USERS + GET_ONE + RESERVATIONS)
    public MessageInfo getReservationsByUserId(@PathVariable Long userId){
        return MessageInfo.success(reservationService.getReservationsByUserId(userId), Arrays.asList("Reservations with UserID = " + userId.toString()));
    }

    @GetMapping(BOOKS + GET_ONE + RESERVATIONS)
    public MessageInfo getReservationsByBookId(@PathVariable Long bookId){
        return MessageInfo.success(reservationService.getReservationsByBookId(bookId), Arrays.asList("Reservations with BookID = " + bookId.toString()));
    }

    @PostMapping(RESERVATIONS + CREATE)
    public MessageInfo createReservation(@RequestBody @Valid ReservationDTO reservationDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, reservationDTO.getClass().getSimpleName());
        return MessageInfo.success(reservationService.createReservation(reservationDTO), Arrays.asList("Reservation created successfully"));
    }

    @DeleteMapping(RESERVATIONS + REMOVE)
    public MessageInfo deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return MessageInfo.success(null, Arrays.asList("Reservation with ID = " + id.toString() + " removed successfully"));
    }
}
