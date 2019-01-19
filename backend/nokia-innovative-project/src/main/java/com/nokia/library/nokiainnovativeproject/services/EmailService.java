package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.Rental;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.repositories.RentalRepository;
import com.nokia.library.nokiainnovativeproject.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final SendEmailService sendEmailService;
	private final ReservationRepository reservationRepository;
	private final RentalRepository rentalRepository;

	@Async
	public void sendSimpleMessage(Email email, List<String> recipients) {

		if(recipients.size()  < 0) return;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipients.toArray(new String[0]));
		message.setSubject(email.getSubject());
		message.setText(email.getMessageContext());
		try {
			sendEmailService.send(message);
		}
		catch(MailException e){
			// attempts to send an email failed
			// we skip the problem
		}
	}

	public void reservationAvailability(Book book){
		List<User> users = reservationRepository.findUserByBook(book.getId());
        Email email = new Email("You can borrow your book now",
				String.format("Book \"%s\" is available now.", book.getBookDetails().getTitle()));
        List<String> userEmails = users.stream().map(User::getEmail).collect(Collectors.toList());
		sendSimpleMessage(email, userEmails);
	}

    @Scheduled(cron = "0 0 20 * * ?")
    @Transactional
	public void renewalsReminder() {
        List<Rental> rentals = rentalRepository.findRentalsForReminder();
        Email email = new Email("Book return", "");
        rentals.forEach(rental -> {
            Hibernate.initialize(rental.getBook().getBookDetails());
            email.setMessageContext(String.format("Book \"%s\" should be returned soon.", rental.getBook().getBookDetails().getTitle()));
            sendSimpleMessage(email, Collections.singletonList(rental.getUser().getEmail()));
        });
    }

    @Scheduled(cron = "0 0 20 * * ?")
    @Transactional
    public void overdueNotice() {
        List<Rental> rentals = rentalRepository.findOverdueRentals();
        Email email = new Email("Book overdue", "");
        rentals.forEach(rental -> {
            Hibernate.initialize(rental.getBook().getBookDetails());
            email.setMessageContext(String.format("Book \"%s\" have to be returned as soon as possible.", rental.getBook().getBookDetails().getTitle()));
            sendSimpleMessage(email, Collections.singletonList(rental.getUser().getEmail()));
        });
    }
}