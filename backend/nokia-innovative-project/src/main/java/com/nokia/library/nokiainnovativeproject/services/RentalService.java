package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.exceptions.BookRentedException;
import com.nokia.library.nokiainnovativeproject.exceptions.BookReservedException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental", "id", id));
    }

    public List<Rental> getRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    public List<Rental> getRentalsByBookId(Long bookId) {
        return rentalRepository.findByBookId(bookId);
    }

    public Rental createRental(RentalDTO rentalDTO) {
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
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rental", "id", id));
        List<Reservation> reservations = reservationService.getReservationsByBookId(rental.getBook().getId());
        if(reservations == null || reservations.isEmpty()){
            rental.setReturnDate(LocalDate.now().plusMonths(1));
            return rentalRepository.save(rental);
        }
        else{
            throw new BookReservedException(rental.getBook().getId());
        }
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rental", "id", id));
        rentalRepository.delete(rental);
    }
}
