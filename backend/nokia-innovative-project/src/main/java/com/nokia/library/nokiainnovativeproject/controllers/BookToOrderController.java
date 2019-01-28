package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.services.BookToOrderService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
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
        return MessageInfo.success(bookToOrderService.getAllBookToOrders(), Arrays.asList("list of books to order"));
    }

    @GetMapping(Mappings.GET_ONE)
    public ResponseEntity getBookToOrderById(@PathVariable Long id) {
        return MessageInfo.success(bookToOrderService.getBookToOrderById(id),
                Arrays.asList("BookToOrderController to order of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public ResponseEntity createBookToOrder(@RequestBody @Valid BookToOrderDTO bookToOrderDTO, BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.createBookToOrder(bookToOrderDTO),
                Arrays.asList("BookToOrder created successfully"));
    }

    @PostMapping(Mappings.UPDATE)
    public ResponseEntity updateBookToOrder(@PathVariable Long id, @RequestBody @Valid BookToOrderDTO bookToOrderDTO,
                                         BindingResult bindingResult) {
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(bookToOrderService.updateBookToOrder(id, bookToOrderDTO),
                Arrays.asList("BookToOrder updated successfully"));
    }

    @DeleteMapping(Mappings.REMOVE)
    public ResponseEntity deleteBookToOrder(@PathVariable Long id) {
        bookToOrderService.deleteBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder with ID = "+  id.toString() + " removed successfully"));
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity acceptBookToOrder(@PathVariable Long id) {
        bookToOrderService.acceptBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder with ID = "+  id.toString() + " has been accepted"));
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity changeSubscribeStatus(@PathVariable Long id){
        bookToOrderService.changeSubscribeStatus(id);
        return MessageInfo.success(null, Arrays.asList("Subscribe status has been changed"));
    }
}