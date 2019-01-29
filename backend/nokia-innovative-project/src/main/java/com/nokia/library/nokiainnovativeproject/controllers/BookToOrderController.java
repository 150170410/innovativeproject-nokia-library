package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.services.BookToOrderService;

import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_TO_ORDER)
public class BookToOrderController {

    private final BookToOrderService bookToOrderService;

    @GetMapping(GET_ALL)
    public ResponseEntity getAllBookToOrder(){
        return MessageInfo.success(bookToOrderService.getAllBookToOrders(), Arrays.asList("list of books to order"));
    }

    @GetMapping(GET_ONE)
    public ResponseEntity getBookToOrderById(@PathVariable Long id) {
        return MessageInfo.success(bookToOrderService.getBookToOrderById(id),
                Arrays.asList("BookToOrderController to order of ID = " + id.toString()));
    }

    @PostMapping(CREATE)
    public ResponseEntity createBookToOrder(@RequestBody @Valid BookToOrderDTO bookToOrderDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.createBookToOrder(bookToOrderDTO),
                Arrays.asList("BookToOrder created successfully"));
    }

    @PostMapping(UPDATE)
    public ResponseEntity updateBookToOrder(@PathVariable Long id, @RequestBody @Valid BookToOrderDTO bookToOrderDTO,
                                         BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.updateBookToOrder(id, bookToOrderDTO),
                Arrays.asList("BookToOrder updated successfully"));
    }

    @DeleteMapping(REMOVE)
    public ResponseEntity deleteBookToOrder(@PathVariable Long id) {
        bookToOrderService.deleteBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder with ID = "+  id.toString() + " removed successfully"));
    }

    @PostMapping(ACCEPT)
    public ResponseEntity acceptBookToOrder(@PathVariable Long id) {
        bookToOrderService.acceptBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder with ID = "+  id.toString() + " has been accepted"));
    }

    @PostMapping(SUBSCRIPTION)
    public ResponseEntity changeSubscribeStatus(@PathVariable Long id){
        bookToOrderService.changeSubscribeStatus(id);
        return MessageInfo.success(null, Arrays.asList("Subscribe status has been changed"));
    }
}