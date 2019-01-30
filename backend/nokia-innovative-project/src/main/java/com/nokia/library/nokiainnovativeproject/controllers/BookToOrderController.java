package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.services.BookToOrderService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_TO_ORDER)
public class BookToOrderController {

    private final BookToOrderService bookToOrderService;

    @GetMapping(Mappings.GET_ALL)
    public ResponseEntity getAllBookToOrder(){
        return MessageInfo.success(bookToOrderService.getAllBookToOrders(),
                Arrays.asList(Messages.get(LIST_OF) + "books to order."));
    }

    @GetMapping(Mappings.USER)
    public ResponseEntity getAllBookToOrderByUser() {
        return MessageInfo.success(bookToOrderService.getBookToOrderByUser(),
                Arrays.asList("User's" + Messages.get(LIST_OF) + "books to order."));
    }

    @GetMapping(Mappings.GET_ONE)
    public ResponseEntity getBookToOrderById(@PathVariable Long id) {
        return MessageInfo.success(bookToOrderService.getBookToOrderById(id),
                Arrays.asList("Book to order" + Messages.get(REQUESTED)));
    }

    @PostMapping(Mappings.CREATE)
    public ResponseEntity createBookToOrder(@RequestBody @Valid BookToOrderDTO bookToOrderDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.createBookToOrder(bookToOrderDTO),
                Arrays.asList("Book to order" + Messages.get(CREATED_SUCCESSFULLY)));
    }

    @PostMapping(Mappings.UPDATE)
    public ResponseEntity updateBookToOrder(@PathVariable Long id, @RequestBody @Valid BookToOrderDTO bookToOrderDTO,
                                         BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.updateBookToOrder(id, bookToOrderDTO),
                Arrays.asList("Book to order" + Messages.get(UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping(Mappings.REMOVE)
    public ResponseEntity deleteBookToOrder(@PathVariable Long id) {
        bookToOrderService.deleteBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder" + Messages.get(REMOVED_SUCCESSFULLY)));
    }
}