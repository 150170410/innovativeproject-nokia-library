package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.exceptions.AlreadyReservedException;
import com.nokia.library.nokiainnovativeproject.exceptions.BookNotRentedException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        //TODO: ADD USER AUTHENTICATION
        Reservation reservation = new Reservation();
        List<Rental> rentals = rentalRepository.findByBookId(reservationDTO.getBookId());
        if (rentals == null || rentals.isEmpty()) {
            CheckUserReservations(reservationDTO.getBookId(), reservationDTO.getUserId());
            reservation.setUser(userService.getUserById(reservationDTO.getUserId()));
            reservation.setBook(bookService.getBookById(reservationDTO.getBookId()));
            return reservationRepository.save(reservation);

        } else {
            throw new BookNotRentedException(reservationDTO.getBookId());
        }
    }

    private void CheckUserReservations(Long bookId, Long userId) {
        List<Reservation> reservations = getReservationsByUserId(userId);
        for(Reservation reservation : reservations){
            if(reservation.getBook().getId() == bookId){
                throw new AlreadyReservedException(bookId, userId);
            }
        }
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "id"));
        reservationRepository.delete(reservation);
    }


}
