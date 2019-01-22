package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.DaysDeltaEnum;
import com.nokia.library.nokiainnovativeproject.utils.ReservationByDateComparator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
	private final EmailService emailService;
	private final UserRepository userRepository;

	public List<Rental> getAllRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		for (Rental rental : rentals) {
			Hibernate.initialize(rental.getBook());
			Hibernate.initialize(rental.getUser());
		}
		return rentals;
	}

	public Rental getRentalById(Long id) {
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("rental"));
		Hibernate.initialize(rental.getBook());
		Hibernate.initialize(rental.getUser());
		return rental;
	}

	public RentalWithActualOwner getRentalsWithActualOwner(Long id){
		return getRentalWithActualOwner(getRentalById(id));
	}

	public List<RentalWithActualOwner> getAllRentalsWithOwner() {
		List<Rental> rentals = getAllRentals();
		List<RentalWithActualOwner> rentalsWithActualOwner = new ArrayList<>();
		for (Rental rental : rentals) {
			rentalsWithActualOwner.add(getRentalWithActualOwner(rental));
		}
		return rentalsWithActualOwner;
	}

	private RentalWithActualOwner getRentalWithActualOwner(Rental rental) {
		ModelMapper mapper = new ModelMapper();
		RentalWithActualOwner rentalWithActualOwner = mapper.map(rental, RentalWithActualOwner.class);
		User user = userRepository.findById(rental.getBook().getActualOwnerId()).orElseThrow( ()-> new ResourceNotFoundException("user"));
		rentalWithActualOwner.setActualOwner(user);
		return  rentalWithActualOwner;
	}

	public List<Rental> getRentalsByUser() {
		User user = userService.getLoggedInUser();
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
		rental = rentalRepository.save(rental);
		User admin = userService.getUserById(borrowedBook.getActualOwnerId());
		Email email = new Email("Book rent", String.format("Your book can be picked up from: %s %s, %s",
				admin.getFirstName(), admin.getLastName(), admin.getAddress().getBuilding()));
		emailService.sendSimpleMessage(email, Arrays.asList(user.getEmail()));
		return rental;
	}


	public Rental prolongRental(Long id) {
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("rental"));
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
		User user = userService.getLoggedInUser();
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("rental"));
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
		User user = userService.getLoggedInUser();
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("rental"));
		Long bookId = rental.getBook().getId();
		Book borrowedBook = bookService.getBookById(bookId);
		List<Reservation> queue = reservationRepository.findByBookId(bookId);
		if (queue.isEmpty()) {
			borrowedBook = bookService.changeState(
					borrowedBook,
					BookStatusEnum.AVAILABLE.getStatusId(),
					0,
					user);
		} else {
			borrowedBook = bookService.changeState(
					borrowedBook,
					BookStatusEnum.RESERVED.getStatusId(),
					DaysDeltaEnum.MINUSMONTH.getDays(),
					user);
		}
		// TODO: put these 2 in transaction
		saveBorrowedBookAndDeleteRental(borrowedBook, rental);
	}

	@Transactional
	public void saveBorrowedBookAndDeleteRental(Book borrowedBook, Rental rental) {
		bookRepository.save(borrowedBook);
		rentalRepository.delete(rental);
	}


	public Rental handOverRental(Long id) {
		User user = userService.getLoggedInUser();
		Rental rental = rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("rental"));
		if (rental.getHandOverDate() != null) {
			throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_HANDED_OVER);
		}
		bookService.changeState(
				rental.getBook(),
				BookStatusEnum.BORROWED.getStatusId(),
				Integer.MAX_VALUE,
				user);
		rental.setHandOverDate(LocalDateTime.now());
		return rentalRepository.save(rental);
	}
}
