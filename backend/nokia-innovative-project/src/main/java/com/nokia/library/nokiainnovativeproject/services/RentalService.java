package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.*;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.ReservationByDateComparator;
import com.nokia.library.nokiainnovativeproject.utils.DaysDeltaEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService {

	private final BookRepository bookRepository;
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

	public List<Rental> getRentalsByUser() {
		User user = userService.getLoggedInUser();
		if (user == null) {
			List<Role> userLoggedInRoles = user.getRoles();
			boolean isAuthorized = false;
			for (Role role : userLoggedInRoles) {
				if (role.getRole().equals("ROLE_USER")) {
					isAuthorized = true;
				}
			}
			if (!isAuthorized) {
				throw new AuthorizationException();
			}
		}
		List<Rental> rentals = rentalRepository.findByUserId(user.getId());
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
		if (user == null) {
			throw new AuthorizationException();
		}

		ModelMapper mapper = new ModelMapper();
		Rental rental = mapper.map(rentalDTO, Rental.class);
		List<Rental> rentals = getRentalsByBookId(rentalDTO.getBookId()).stream().filter(Rental::getIsCurrent).collect(Collectors.toList());
		if (rentals != null && !rentals.isEmpty()) {
			throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_RENTED);
		}
		List<Reservation> reservations = reservationRepository.findByBookId(rentalDTO.getBookId());
		if (reservations != null && !reservations.isEmpty()) {
			Collections.sort(reservations, new ReservationByDateComparator());
			if (!reservations.get(0).getUser().equals(user)) {
				throw new InvalidBookStateException(MessageTypes.BOOK_RESERVED);
			}

		}
		Long bookId = rentalDTO.getBookId();
		Book borrowedBook = bookService.getBookById(bookId);
		rental.setBook(bookService.changeState(
				borrowedBook,
				BookStatusEnum.AWAITING.getStatusId(),
				30,
				user));
		rental.setUser(user);
		return rentalRepository.save(rental);
	}


	public Rental prolongRental(Long id) {
		//TODO: ADD USER AUTHENTICATION
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		if (!rental.getIsCurrent()) {
			throw new InvalidBookStateException(MessageTypes.RENTAL_OBSOLETE);
		}
		if (rental.getReturnDate().minusWeeks(1).compareTo(LocalDateTime.now()) > 0) {
			throw new InvalidBookStateException(MessageTypes.PROLONG_NOT_AVAILABLE);
		}
		List<Reservation> reservations = reservationRepository.findByBookId(rental.getBook().getId());
		if (reservations == null || reservations.isEmpty()) {
			rental.setReturnDate(rental.getReturnDate().plusMonths(1));
			rental.getBook().setAvailableDate(rental.getReturnDate());
			return rentalRepository.save(rental);
		} else {
			throw new InvalidBookStateException(MessageTypes.PROLONG_NOT_AVAILABLE);
		}
	}

	public Rental returnRental(Long id) {
		//TODO: ADD ADMIN AUTHENTICATION
		User user = userService.getLoggedInUser();
		if (user == null) {
			List<Role> userLoggedInRoles = user.getRoles();
			boolean isAuthorized = false;
			for (Role role : userLoggedInRoles) {
				if (role.getRole().equals("ROLE_ADMIN")) {
					isAuthorized = true;
				}
			}
			if (!isAuthorized) {
				throw new AuthorizationException();
			}
		}
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		List<Reservation> usersQueue = reservationRepository.findByBookId(rental.getId());

		if (usersQueue.isEmpty()) {
			bookService.changeState(
					rental.getBook(),
					BookStatusEnum.AVAILABLE.getStatusId(),
					0,
					user);
		} else {
			bookService.changeState(
					rental.getBook(),
					BookStatusEnum.RESERVED.getStatusId(),
					0,
					user);
		}
		if (!rental.getIsCurrent()) {
			throw new InvalidBookStateException(MessageTypes.RENTAL_OBSOLETE);
		}
		rental.setReturnDate(LocalDateTime.now());
		rental.setIsCurrent(false);
		return rentalRepository.save(rental);
	}

	public void deleteRental(Long id) {
		//TODO: ADD USER AUTHENTICATION
		User user = userService.getLoggedInUser();
		if (user == null) {
			List<Role> userLoggedInRoles = user.getRoles();
			boolean isAuthorized = false;
			for (Role role : userLoggedInRoles) {
				if (role.getRole().equals("ROLE_USER")) {
					isAuthorized = true;
				}
			}
			if (!isAuthorized) {
				throw new AuthorizationException();
			}
		}
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		Long bookId = rental.getBook().getId();
		Book borrowedBook = bookService.getBookById(bookId);
		List<Rental> queue = rentalRepository.findByBookId(bookId);

		if (queue.isEmpty()) {
			rental.setBook(bookService.changeState(
					borrowedBook,
					BookStatusEnum.AVAILABLE.getStatusId(),
					0,
					user));
		} else {
			rental.setBook(bookService.changeState(
					borrowedBook,
					BookStatusEnum.RESERVED.getStatusId(),
					DaysDeltaEnum.MINUSMONTH.getDays(),
					user));
		}

		// TODO: put these 2 in transaction
		bookRepository.save(borrowedBook);
		rentalRepository.delete(rental);
	}


	public Rental handOverRental(Long id) {
		User user = userService.getLoggedInUser();
		if (user == null) {
			List<Role> userLoggedInRoles = user.getRoles();
			boolean isAuthorized = false;
			for (Role role : userLoggedInRoles) {
				if (role.getRole().equals("ROLE_ADMIN")) {
					isAuthorized = true;
				}
			}
			if (!isAuthorized) {
				throw new AuthorizationException();
			}
		}

		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		if (rental.getHandOverDate() != null) {
			throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_HANDED_OVER);
		}
		bookService.changeState(
				rental.getBook(),
				BookStatusEnum.BORROWED.getStatusId(),
				0,
				user);
		rental.setHandOverDate(LocalDateTime.now());
		return rentalRepository.save(rental);
	}
}
