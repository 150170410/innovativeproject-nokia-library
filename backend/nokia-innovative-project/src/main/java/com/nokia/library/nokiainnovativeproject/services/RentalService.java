package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.exceptions.*;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            throw new BookRentedException(rentalDTO.getBookId());
        }
        rental.setUser(userService.getUserById(rentalDTO.getUserId()));
        rental.setBook(bookService.getBookById(rentalDTO.getBookId()));
        return rentalRepository.save(rental);
    }


    public Rental updateRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));

        if(rental.getReturnDate().minusWeeks(1).compareTo(LocalDate.now()) > 0){
               throw new ProlongationForbiddenException(rental.getBook().getId(), rental.getReturnDate().minusWeeks(1).toString());
        }

        List<Reservation> reservations = reservationRepository.findByBookId(rental.getBook().getId());
        if(reservations == null || reservations.isEmpty()){
            rental.setReturnDate(rental.getReturnDate().plusMonths(1));
            return rentalRepository.save(rental);
        }
        else{
            throw new BookReservedException(rental.getBook().getId());
        }
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        rentalRepository.delete(rental);
    }

    public Rental handOverRental(Long id) {
        //TODO: ADD ADMIN AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        if(rental.getHandOverDate() != null){throw new AlreadyHandedOverException(id);}
        rental.setHandOverDate(LocalDate.now());
        return rentalRepository.save(rental);
    }
}
