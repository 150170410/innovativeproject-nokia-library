package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.*;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService {

	private final RentalRepository rentalRepository;
	private final ReservationRepository reservationRepository;
	private final UserService userService;
	private final BookService bookService;

	public List<Rental> getAllRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		for (Rental rental : rentals) {
			Hibernate.initialize(rental.getBook());
			Hibernate.initialize(rental.getUser());
		}
		return rentals;
	}

	public Rental getRentalById(Long id) {
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		Hibernate.initialize(rental.getBook());
		Hibernate.initialize(rental.getUser());
		return rental;
	}

	public List<Rental> getRentalsByUserId(Long userId) {
		List<Rental> rentals = rentalRepository.findByUserId(userId);
		for (Rental rental : rentals) {
			Hibernate.initialize(rental.getBook());
			Hibernate.initialize(rental.getUser());
		}
		return rentals;
	}

	public List<Rental> getRentalsByBookId(Long bookId) {
		List<Rental> rentals = rentalRepository.findByBookId(bookId);
		for (Rental rental : rentals) {
			Hibernate.initialize(rental.getBook());
			Hibernate.initialize(rental.getUser());
		}
		return rentals;
	}

	public Rental createRental(RentalDTO rentalDTO) {
		User user = userService.getLoggedInUser();
		if(user == null) {
			throw new AuthorizationException();
		}
		ModelMapper mapper = new ModelMapper();
		Rental rental = mapper.map(rentalDTO, Rental.class);
		rental.setUser(user);
		List<Rental> rentals = getRentalsByBookId(rentalDTO.getBookId());
		if (rentals != null && !rentals.isEmpty()) {
			throw new BookRentedException(rentalDTO.getBookId());
		}
		Book borrowedBook = bookService.getBookById(rentalDTO.getBookId());
		rental.setBook(bookService.changeStatus(borrowedBook, 2L));
		rental.setUser(userService.getLoggedInUser());

		return rentalRepository.save(rental);
	}

	public Rental updateRental(Long id) {
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));

		if (rental.getReturnDate().minusWeeks(1).compareTo(LocalDate.now()) > 0) {
			throw new ProlongationForbiddenException(rental.getBook().getId(), rental.getReturnDate().minusWeeks(1).toString());
		}

		List<Reservation> reservations = reservationRepository.findByBookId(rental.getBook().getId());
		if (reservations == null || reservations.isEmpty()) {
			rental.setReturnDate(rental.getReturnDate().plusMonths(1));
			rental.getBook().setAvailableDate(rental.getReturnDate());
			return rentalRepository.save(rental);
		} else {
			throw new BookReservedException(rental.getBook().getId());
		}
	}

	public void deleteRental(Long id) {
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		rental.getBook().setAvailableDate(null);
		rentalRepository.delete(rental);
	}

	public Rental handOverRental(Long id) {
		User user = userService.getLoggedInUser();
		if (user == null){
			List<Role> userLoggedInRoles = user.getRoles();
			boolean isAuthorized = false;
			for(Role role : userLoggedInRoles) {
				if(role.getRole().equals("ROLE_ADMIN")){
					isAuthorized = true;
				}
			}
			if(!isAuthorized){
				throw new AuthorizationException();
			}
		}
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		if (rental.getHandOverDate() != null) {
			throw new AlreadyHandedOverException(id);
		}
		rental.setHandOverDate(LocalDate.now());
		rental.getBook().setAvailableDate(null);
		return rentalRepository.save(rental);
	}
}
