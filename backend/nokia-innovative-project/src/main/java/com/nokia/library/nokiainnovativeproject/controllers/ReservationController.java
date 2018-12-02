package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.services.ReservationService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION )
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping(Mappings.RESERVATIONS + Mappings.GET_ALL)
    public MessageInfo getAllReservations(){
        return MessageInfo.success(reservationService.getAllReservations(), Arrays.asList("Full list of book reservations"));
    }

    @GetMapping(Mappings.RESERVATIONS + Mappings.GET_ONE)
    public MessageInfo getReservationById(@PathVariable Long id){
        return MessageInfo.success(reservationService.getReservationById(id), Arrays.asList("Reservation with ID = " + id.toString()));
    }

    @GetMapping(Mappings.USERS + Mappings.GET_ONE + Mappings.RESERVATIONS)
    public MessageInfo getReservationsByUserId(@PathVariable Long userId){
        return MessageInfo.success(reservationService.getReservationsByUserId(userId), Arrays.asList("Reservations with UserID = " + userId.toString()));
    }

    @GetMapping(Mappings.BOOKS + Mappings.GET_ONE + Mappings.RESERVATIONS)
    public MessageInfo getReservationsByBookId(@PathVariable Long bookId){
        return MessageInfo.success(reservationService.getReservationsByBookId(bookId), Arrays.asList("Reservations with BookID = " + bookId.toString()));
    }

    @PostMapping(Mappings.RESERVATIONS + Mappings.CREATE)
    public MessageInfo createReservation(@RequestBody @Valid ReservationDTO reservationDTO, BindingResult bindingResult){
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : MessageInfo.success(reservationService.createReservation(reservationDTO), Arrays.asList("Reservation created successfully"));
    }

    @DeleteMapping(Mappings.RESERVATIONS + Mappings.REMOVE)
    public MessageInfo deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return MessageInfo.success(null, Arrays.asList("Reservation with ID = " + id.toString() + " removed successfully"));
    }
}
