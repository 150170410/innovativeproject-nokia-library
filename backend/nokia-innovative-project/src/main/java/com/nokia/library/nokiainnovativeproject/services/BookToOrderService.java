package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookToOrderRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BookToOrderService {

    private final BookToOrderRepository bookToOrderRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    public List<BookToOrder> getAllBookToOrders() {
        List<BookToOrder> booksToOrder = bookToOrderRepository.findAll();
        for(BookToOrder bookToOrder: booksToOrder) {
            Hibernate.initialize(bookToOrder.getUser());
        }
        return booksToOrder;
    }

    public BookToOrder getBookToOrderById(Long id) {
        BookToOrder bookToOrder = bookToOrderRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("bookToOrder"));
        Hibernate.initialize(bookToOrder.getUser());
        return bookToOrder;
    }

    public BookToOrder createBookToOrder(BookToOrderDTO bookToOrderDTO) {
        ModelMapper mapper = new ModelMapper();
        BookToOrder bookToOrder = mapper.map(bookToOrderDTO, BookToOrder.class);
        bookToOrder.setUser(userService.getLoggedInUser());

        List<String> adminsEmail = userRepository.getAdminsEmail();
        emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
        return bookToOrderRepository.save(bookToOrder);
    }

    public BookToOrder updateBookToOrder(Long id, BookToOrderDTO bookToOrderDTO) {
        BookToOrder bookToOrder= bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
        bookToOrder.setIsbn(bookToOrderDTO.getIsbn());
        bookToOrder.setTitle(bookToOrderDTO.getTitle());
        bookToOrder.setUser(userService.getLoggedInUser());

        List<String> adminsEmail = userRepository.getAdminsEmail();
        emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
        return bookToOrderRepository.save(bookToOrder);
    }

    public void deleteBookToOrder(Long id) {
        BookToOrder bookToOrder= bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
        bookToOrderRepository.delete(bookToOrder);
    }

    private Email createMessage(BookToOrder bookToOrder) {
        Email email = new Email();
        email.setMessageContext("The website user asked to buy a book with the following parameters" +
                " Title: " + bookToOrder.getTitle() + ", Isbn: " + bookToOrder.getIsbn() + ".");
        email.setSubject("New book request: " + bookToOrder.getTitle() + ".");
        return email;
    }
}