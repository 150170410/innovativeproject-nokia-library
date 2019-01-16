package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;

import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes;

import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.DaysDeltaEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

	private final BookRepository bookRepository;
	private final RentalRepository rentalRepository;
	private final ReservationRepository reservationRepository;
	private final UserService userService;
	private final BookService bookService;


	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = reservationRepository.findAll();
		for (Reservation reservation : reservations) {
			Hibernate.initialize(reservation.getBook());
			Hibernate.initialize(reservation.getUser());
		}
		return reservations;
	}

	public Reservation getReservationById(Long id) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("reservation"));
		Hibernate.initialize(reservation.getBook());
		Hibernate.initialize(reservation.getUser());
		return reservation;
	}

	public List<Reservation> getReservationsByUserId(Long userId) {
		return reservationRepository.findByUserId(userId);
	}

	public List<Reservation> getReservationsByUser() {
		User user = userService.getLoggedInUser();
		return reservationRepository.findByUserId(user.getId());
	}

	public List<Reservation> getReservationsByBookId(Long bookId) {
		return reservationRepository.findByBookId(bookId);
	}

	public Reservation createReservation(ReservationDTO reservationDTO) {
		User user = userService.getLoggedInUser();
		Reservation reservation = new Reservation();
		List<Rental> rentals = rentalRepository.findByBookId(reservationDTO.getBookId()).stream()
				.filter(Rental::getIsCurrent).collect(Collectors.toList());
		if (rentals == null || rentals.isEmpty()) {
			throw new InvalidBookStateException(MessageTypes.NOT_RENTED);
		}
		checkUserReservations(reservationDTO.getBookId(), user.getId());
		checkUserRentals(reservationDTO.getBookId(), user.getId());
		reservation.setUser(user);
		Long bookId = reservationDTO.getBookId();
		Book borrowedBook = bookService.getBookById(bookId);
		reservation.setBook(bookService.changeState(
				borrowedBook,
				borrowedBook.getStatus().getId(),
				DaysDeltaEnum.PLUSMONTH.getDays(),
				user));
		reservation.setAvailableDate(reservation.getBook().getAvailableDate());
		return reservationRepository.save(reservation);
	}

	private void checkUserReservations(Long bookId, Long userId) {
		List<Reservation> reservations = getReservationsByUserId(userId);
		for (Reservation reservation : reservations) {
			if (reservation.getBook().getId().equals(bookId)) {
				throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_RESERVED);
			}
		}
	}

	private void checkUserRentals(Long bookId, Long userId) {
		List<Rental> rentals = rentalRepository.findByUserId(userId).stream().filter(Rental::getIsCurrent).collect(Collectors.toList());
		for (Rental rental : rentals) {
			if (rental.getBook().getId().equals(bookId)) {
				throw new InvalidBookStateException(MessageTypes.BOOK_ALREADY_IN_POSSESSION);
			}
		}
	}

	public void deleteReservation(Long id) {
		User user = userService.getLoggedInUser();
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("reservation"));
		Long bookId = reservation.getBook().getId();
		Book borrowedBook = bookService.getBookById(bookId);
		List<Reservation> queue = reservationRepository.findByBookId(bookId);
		if (borrowedBook.getStatus().getId().equals(BookStatusEnum.BORROWED.getStatusId())) {
			borrowedBook = bookService.changeState(
					borrowedBook,
					borrowedBook.getStatus().getId(),
					DaysDeltaEnum.MINUSMONTH.getDays(),
					user);
		} else if (borrowedBook.getStatus().getId().equals(BookStatusEnum.RESERVED.getStatusId())) {
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
		}
		// TODO: put these 2 in transaction
		saveBorrowedBookAndDeleteReservation(borrowedBook, reservation);
	}
	@Transactional
	public void saveBorrowedBookAndDeleteReservation(Book borrowedBook, Reservation reservation) {
		bookRepository.save(borrowedBook);
		reservationRepository.delete(reservation);
	}
}
