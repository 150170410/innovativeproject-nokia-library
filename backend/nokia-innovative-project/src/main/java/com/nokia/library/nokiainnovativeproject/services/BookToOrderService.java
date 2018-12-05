package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookToOrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookToOrderService {

    private final BookToOrderRepository bookToOrderRepository;
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

        Email email = new Email();



        return bookToOrderRepository.save(bookToOrder);
    }

    public BookToOrder updateBookToOrder(Long id, BookToOrderDTO bookToOrderDTO) {
        BookToOrder bookToOrder= bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
        bookToOrder.setIsbn(bookToOrderDTO.getIsbn());
        bookToOrder.setTitle(bookToOrderDTO.getTitle());




        return bookToOrderRepository.save(bookToOrder);
    }

    public void deleteBookToOrder(Long id) {
        BookToOrder bookToOrder= bookToOrderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("bookToOrder"));
        bookToOrderRepository.delete(bookToOrder);
    }
}
