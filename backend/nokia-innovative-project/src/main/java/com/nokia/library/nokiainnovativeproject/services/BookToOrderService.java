package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookToOrderRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookToOrderService {

	private final BookToOrderRepository bookToOrderRepository;
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final UserService userService;

	public List<BookToOrderDTO> getAllBookToOrders() {
		List<BookToOrder> booksToOrder = bookToOrderRepository.findAll();
		List<BookToOrderDTO> bookDTOs = new ArrayList<>();
		User loggedInUser = userService.getLoggedInUser();
		ModelMapper mapper = new ModelMapper();
		for (BookToOrder book : booksToOrder) {
			Hibernate.initialize(book.getUsers());
			BookToOrderDTO bookDTO = mapper.map(book, BookToOrderDTO.class);
			bookDTO.setSubscribed(book.getUsers().contains(loggedInUser));
			bookDTO.setTotalSubs(book.getUsers().size());
			bookDTO.setRemovable(book.getRequestCreator().equals(loggedInUser) && book.getUsers().size() == 1);
			bookDTOs.add(bookDTO);
		}
		return bookDTOs;
	}

	public BookToOrderDTO getBookToOrderById(Long id) {
		BookToOrder book = bookToOrderRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("bookToOrder"));
		BookToOrderDTO bookDTO = new ModelMapper().map(book, BookToOrderDTO.class);
		User loggedInUser = userService.getLoggedInUser();
		Hibernate.initialize(book.getUsers());
		bookDTO.setSubscribed(book.getUsers().contains(loggedInUser));
		bookDTO.setTotalSubs(book.getUsers().size());
		bookDTO.setRemovable(book.getRequestCreator().equals(loggedInUser) && book.getUsers().size() == 1);
		return bookDTO;
	}

	public BookToOrder createBookToOrder(BookToOrderDTO bookToOrderDTO) {
		ModelMapper mapper = new ModelMapper();
		BookToOrder bookToOrder = mapper.map(bookToOrderDTO, BookToOrder.class);
		List<User> users = new ArrayList<>();
		bookToOrder.setRequestCreator(userService.getLoggedInUser());
		users.add(userService.getLoggedInUser());
		bookToOrder.setUsers(users);

		List<String> adminsEmail = userRepository.getAdminsEmail();
		emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
		return bookToOrderRepository.save(bookToOrder);
	}

	@Transactional
	public void changeSubscribeStatus(Long id) {
		BookToOrder book = bookToOrderRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("bookToOrder"));
		Hibernate.initialize(book.getUsers());
		if (book.getUsers().contains(userService.getLoggedInUser())) {
			book.getUsers().remove(userService.getLoggedInUser());
		} else {
			book.getUsers().add(userService.getLoggedInUser());
		}
		bookToOrderRepository.save(book);
	}

	public BookToOrder updateBookToOrder(Long id, BookToOrderDTO bookToOrderDTO) {
		BookToOrder bookToOrder = bookToOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("bookToOrder"));
		bookToOrder.setIsbn(bookToOrderDTO.getIsbn());
		bookToOrder.setTitle(bookToOrderDTO.getTitle());
		List<String> adminsEmail = userRepository.getAdminsEmail();
		emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
		return bookToOrderRepository.save(bookToOrder);
	}

	public void deleteBookToOrder(Long id) {
		BookToOrder bookToOrder = bookToOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("bookToOrder"));
		bookToOrderRepository.delete(bookToOrder);
	}

	public void acceptBookToOrder(Long id, Book book) {
		BookToOrder bookToOrder = bookToOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("bookToOrder"));
		bookToOrderRepository.delete(bookToOrder);
		Email email = new Email();
		email.setMessageContext("The requested book: " + book.getBookDetails().getTitle() + " is finally available in the library: https://nokia-library-client.herokuapp.com/book/" + book.getBookDetails().getId());
		email.setSubject("New book request: " + bookToOrder.getTitle() + ".");
		Hibernate.initialize(bookToOrder.getUsers());
		emailService.sendSimpleMessage(email, bookToOrder.getUsers().stream().map(User::getEmail).collect(Collectors.toList()));
	}

	private Email createMessage(BookToOrder bookToOrder) {
		Email email = new Email();
		email.setMessageContext("The user asked to buy a book with the following parameters" +
				" Title: " + bookToOrder.getTitle() + ", Isbn: " + bookToOrder.getIsbn() + ".");
		email.setSubject("New book request: " + bookToOrder.getTitle() + ".");
		return email;
	}

	public BookToOrder getBookToOrderByIsbn(String isbn) {
		return bookToOrderRepository.getBookToOrdersByIsbn(isbn).orElse(null);
	}

	@Scheduled(cron = "0 0 22 * * ?")
	@Transactional
	public void removeOverdueBookToOrder() {
		bookToOrderRepository.removeOverdueBooks();
	}
}