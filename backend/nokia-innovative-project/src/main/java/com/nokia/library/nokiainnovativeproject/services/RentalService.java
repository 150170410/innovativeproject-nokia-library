package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.utils.ReservationByDateComparator;
import com.nokia.library.nokiainnovativeproject.exceptions.*;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookService bookService;
    private final ReservationRepository reservationRepository;


    public List<Rental> getAllRentals() {
        List<Rental> rentals =  rentalRepository.findAll();
        for ( Rental rental: rentals ){
            Hibernate.initialize(rental.getBook());
            Hibernate.initialize(rental.getUser());
        }
        return rentals;
    }

    public Rental getRentalById(Long id) {
        Rental rental  =  rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException( "id"));
        Hibernate.initialize(rental.getBook());
        Hibernate.initialize(rental.getUser());
        return rental;
    }

    public List<Rental> getRentalsByUserId(Long userId) {
        List<Rental> rentals =  rentalRepository.findByUserId(userId);
        for ( Rental rental: rentals ){
            Hibernate.initialize(rental.getBook());
            Hibernate.initialize(rental.getUser());
        }
        return rentals;
    }

    public List<Rental> getRentalsByBookId(Long bookId) {
        List<Rental> rentals =  rentalRepository.findByBookId(bookId);
        for ( Rental rental: rentals ){
            Hibernate.initialize(rental.getBook());
            Hibernate.initialize(rental.getUser());
        }
        return rentals;
    }

    public Rental createRental(RentalDTO rentalDTO) {
        //TODO: ADD USER AUTHENTICATION
        ModelMapper mapper = new ModelMapper();
        Rental rental = mapper.map(rentalDTO, Rental.class);
        List<Rental> rentals = getRentalsByBookId(rentalDTO.getBookId());
        if(rentals != null && !rentals.isEmpty() ){
            throw new InvalidBookStateException(rentalDTO.getBookId(), "Book already rented.");
        }
        List<Reservation> reservations = reservationRepository.findByBookId(rentalDTO.getBookId());
        if(reservations != null && !reservations.isEmpty()){
            Collections.sort(reservations, new ReservationByDateComparator());
            if(!reservations.get(0).getUser().getId().equals(rentalDTO.getUserId())){
                throw new InvalidBookStateException(rentalDTO.getBookId(), "Book is reserved for another user.");
            }

        }
        rental.setUser(userService.getUserById(rentalDTO.getUserId()));
        rental.setBook(bookService.getBookById(rentalDTO.getBookId()));
        return rentalRepository.save(rental);
    }


    public Rental prolongRental(Long id) {
        //TODO: ADD USER AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        if(!rental.getIsCurrent()){
            throw new InvalidBookStateException(id, "This rental is obsolete.");
        }
        if(rental.getReturnDate().minusWeeks(1).compareTo(LocalDate.now()) > 0) {
            throw new InvalidBookStateException(rental.getBook().getId(), String.format("Cannot prolong until %s", rental.getReturnDate().minusWeeks(1).toString()));
        }
        List<Reservation> reservations = reservationRepository.findByBookId(rental.getBook().getId());
        if(reservations == null || reservations.isEmpty()){
            rental.setReturnDate(rental.getReturnDate().plusMonths(1));
            return rentalRepository.save(rental);
        }
        else{
            throw new InvalidBookStateException(rental.getBook().getId(),"Book is reserved by another user");
        }
    }

    public Rental returnRental(Long id) {
        //TODO: ADD ADMIN AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        rental.setReturnDate(LocalDate.now());
        rental.setIsCurrent(false);
        return rentalRepository.save(rental);
    }

    public Rental handOverRental(Long id) {
        //TODO: ADD ADMIN AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        if(rental.getHandOverDate() != null){throw new InvalidBookStateException(id, null);}
        rental.setHandOverDate(LocalDate.now());
        return rentalRepository.save(rental);
    }
}
