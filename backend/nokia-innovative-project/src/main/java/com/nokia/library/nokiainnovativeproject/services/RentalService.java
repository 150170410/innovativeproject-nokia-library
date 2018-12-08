package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Review;
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
        rental.setUser(userService.getUserById(rentalDTO.getUserId()));
        rental.setBook(bookService.getBookById(rentalDTO.getBookId()));
        return rentalRepository.save(rental);
    }

    //TODO Implement prolongation functionality
    public Rental updateRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rental", "id", id));
        rental.setReturnDate(LocalDate.now().plusMonths(1));
        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rental", "id", id));
        rentalRepository.delete(rental);
    }
}
