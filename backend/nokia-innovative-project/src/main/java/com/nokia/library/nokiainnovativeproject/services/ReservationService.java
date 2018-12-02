package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final BookService bookService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ReservationService", "id", id));
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByBookId(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setUser(userService.getUserById(reservationDTO.getUserId()));
        reservation.setBook(bookService.getBookById(reservationDTO.getBookId()));
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.delete(reservation);
    }


}
