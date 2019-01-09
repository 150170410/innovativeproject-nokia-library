package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import com.nokia.library.nokiainnovativeproject.utils.ReservationByDateComparator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

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
        List<Rental> rentals = getRentalsByBookId(rentalDTO.getBookId()).stream().filter(Rental::getIsCurrent).collect(Collectors.toList());
        if(rentals != null && !rentals.isEmpty() ){
            throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_RENTED);
        }
        List<Reservation> reservations = reservationRepository.findByBookId(rentalDTO.getBookId());
        if(reservations != null && !reservations.isEmpty()){
            Collections.sort(reservations, new ReservationByDateComparator());
            if(!reservations.get(0).getUser().getId().equals(rentalDTO.getUserId())){
                throw new InvalidBookStateException(MessageTypes.BOOK_RESERVED);
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
            throw new InvalidBookStateException(MessageTypes.RENTAL_OBSOLETE);
        }
        if(rental.getReturnDate().minusWeeks(1).compareTo(LocalDate.now()) > 0) {
            throw new InvalidBookStateException(MessageTypes.PROLONG_NOT_AVAILABLE);
        }
        List<Reservation> reservations = reservationRepository.findByBookId(rental.getBook().getId());
        if(reservations == null || reservations.isEmpty()){
            rental.setReturnDate(rental.getReturnDate().plusMonths(1));
            return rentalRepository.save(rental);
        }
        else{
            throw new InvalidBookStateException(MessageTypes.PROLONG_NOT_AVAILABLE);
        }
    }

    public Rental returnRental(Long id) {
        //TODO: ADD ADMIN AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        if(!rental.getIsCurrent()){
            throw new InvalidBookStateException(MessageTypes.RENTAL_OBSOLETE);
        }
        rental.setReturnDate(LocalDate.now());
        rental.setIsCurrent(false);
        return rentalRepository.save(rental);
    }

    public Rental handOverRental(Long id) {
        //TODO: ADD ADMIN AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id"));
        if(rental.getHandOverDate() != null){throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_HANDED_OVER);}
        rental.setHandOverDate(LocalDate.now());
        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        //TODO: ADD USER AUTHENTICATION
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "id"));
        if(rental.getHandOverDate() != null){
            throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_HANDED_OVER);
        }
        rentalRepository.delete(rental);
    }
}
