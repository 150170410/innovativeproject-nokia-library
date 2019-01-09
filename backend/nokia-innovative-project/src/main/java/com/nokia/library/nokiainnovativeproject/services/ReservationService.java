package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.AuthorizationException;

import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final BookService bookService;
    private final RentalRepository rentalRepository;


    public List<Reservation> getAllReservations() {
        List<Reservation> reservations =  reservationRepository.findAll();
        for ( Reservation reservation: reservations ){
            Hibernate.initialize(reservation.getBook());
            Hibernate.initialize(reservation.getUser());
        }
        return reservations;
    }

    public Reservation getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "id"));
        Hibernate.initialize(reservation.getBook());
        Hibernate.initialize(reservation.getUser());
        return reservation;
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByBookId(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        User user = userService.getLoggedInUser();
        if(user == null) {
            throw new AuthorizationException();
        }
        Reservation reservation = new Reservation();
        List<Rental> rentals = rentalRepository.findByBookId(reservationDTO.getBookId()).stream().filter(Rental::getIsCurrent).collect(Collectors.toList());
        if (rentals == null || rentals.isEmpty()) {
            throw new InvalidBookStateException(MessageTypes.NOT_RENTED);
        }
        checkUserReservations(reservationDTO.getBookId(), user.getId());
        checkUserRentals(reservationDTO.getBookId(), user.getId());
        reservation.setUser(user);
        reservation.setBook(bookService.getBookById(reservationDTO.getBookId()));
        return reservationRepository.save(reservation);
    }

    private void checkUserReservations(Long bookId, Long userId) {
        List<Reservation> reservations = getReservationsByUserId(userId);
        for(Reservation reservation : reservations){
            if(reservation.getBook().getId().equals(bookId)){
                throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_RESERVED);
            }
        }
    }
    private void checkUserRentals(Long bookId, Long userId) {
        List<Rental> rentals = rentalRepository.findByUserId(userId).stream().filter(Rental::getIsCurrent).collect(Collectors.toList());
        for(Rental rental : rentals){
            if (rental.getBook().getId().equals(bookId)) {
                    throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_IN_POSSESSION);
                }
        }
    }

    public void deleteReservation(Long id) {
        //TODO: ADD USER AUTHENTICATION
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "id"));
        reservationRepository.delete(reservation);
    }


}
