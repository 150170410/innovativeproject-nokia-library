package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookToOrderRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookToOrderService {

    private final BookToOrderRepository bookToOrderRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<BookToOrder> getAllBookToOrders() {
        return bookToOrderRepository.findAll();
    }

    public BookToOrder getBookToOrderById(Long id) {
        return bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
    }

    public BookToOrder createBookToOrder(BookToOrderDTO bookToOrderDTO) {
        ModelMapper mapper = new ModelMapper();
        BookToOrder bookToOrder = mapper.map(bookToOrderDTO, BookToOrder.class);

        List<String> adminsEmail = userRepository.getAdminsEmail();
        try {
            emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
        }
        catch(MailException e) {
        }

        return bookToOrderRepository.save(bookToOrder);
    }

    public BookToOrder updateBookToOrder(Long id, BookToOrderDTO bookToOrderDTO) {
        BookToOrder bookToOrder= bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
        bookToOrder.setIsbn(bookToOrderDTO.getIsbn());
        bookToOrder.setTitle(bookToOrderDTO.getTitle());

        List<String> adminsEmail = userRepository.getAdminsEmail();
        try {
            emailService.sendSimpleMessage(createMessage(bookToOrder), adminsEmail);
        }
        catch(MailException e) {
        }

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