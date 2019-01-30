package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReservationDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.AuthorizationException;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;

import static com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum.*;

import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.DaysDeltaEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes;

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
				null));
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

	public void acceptReservation(Long id) {
		User user = userService.getLoggedInUser();
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("reservation"));
		Book borrowedBook = bookService.getBookById(reservation.getBook().getId());

		validateUser(user, reservation);

		validateBookStatus(borrowedBook, RESERVED);

		borrowedBook = bookService.changeState(
				borrowedBook,
				BookStatusEnum.AWAITING.getId(),
				Integer.MAX_VALUE,
				null);
		Rental rental = new Rental();
		rental.setBook(borrowedBook);
		rental.setUser(user);

		bookRepository.save(borrowedBook);
		rentalRepository.save(rental);
		reservationRepository.delete(reservation);

	}

	public void rejectReservation(Long id) {
		User user = userService.getLoggedInUser();
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("reservation"));
		Long bookId = reservation.getBook().getId();
		Book borrowedBook = bookService.getBookById(bookId);
		List<Reservation> queue = reservationRepository.findByBookId(bookId);

		validateUser(user, reservation);
		
		validateBookStatus(borrowedBook, RESERVED);

		if (queue.size() == 1) {
			borrowedBook = bookService.changeState(
					borrowedBook,
					BookStatusEnum.AVAILABLE.getId(),
					DaysDeltaEnum.RESET.getDays(),
					null);
		} else {
			borrowedBook = bookService.changeState(
					borrowedBook,
					BookStatusEnum.RESERVED.getId(),
					DaysDeltaEnum.MINUSMONTH.getDays(),
					null);
		}
		saveBorrowedBookAndDeleteReservation(borrowedBook, reservation);
		updateReservationsQueue(queue, DaysDeltaEnum.MINUSMONTH.getDays());
	}

	public void cancelReservation(Long id) {
		User user = userService.getLoggedInUser();
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("reservation"));
		Book borrowedBook = bookService.getBookById(reservation.getBook().getId());

		validateUser(user, reservation);

		borrowedBook = bookService.changeState(
				borrowedBook,
				borrowedBook.getStatus().getId(),
				DaysDeltaEnum.MINUSMONTH.getDays(),
				null);
		saveBorrowedBookAndDeleteReservation(borrowedBook, reservation);
		List<Reservation> queue = reservationRepository.findByBookId(borrowedBook.getId());
		updateReservationsQueue(queue, DaysDeltaEnum.MINUSMONTH.getDays());

	}

	@Transactional
	public void saveBorrowedBookAndDeleteReservation(Book borrowedBook, Reservation reservation) {
		bookRepository.save(borrowedBook);
		reservationRepository.delete(reservation);
	}

	public void updateReservationsQueue(List<Reservation> queue, Integer days) {
		for (Reservation reservation : queue) {
			if (days == -31) {
				reservation.setAvailableDate(reservation.getAvailableDate().minusMonths(1));
			} else if (-31 < days && days < 0) {
				reservation.setAvailableDate(reservation.getAvailableDate().minusDays(-1 * days));
			}
		}
	}

	private void validateUser(User user, Reservation reservation) {
		if (reservation.getUser().getId().equals(user.getId())) {
			throw new AuthorizationException();
		}
	}

	private void validateBookStatus(Book borrowedBook, BookStatusEnum bookStatusEnum) {
		if (borrowedBook.getStatus().getId() != bookStatusEnum.getId()) {
			throw new InvalidBookStateException(MessageTypes.BOOK_RESERVED);
		}
	}

}