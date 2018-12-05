package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.services.BookToOrderService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
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
    public MessageInfo getAllBookToOrder(){
        return MessageInfo.success(bookToOrderService.getAllBookToOrders(), Arrays.asList("list of books to order"));
    }

    @GetMapping(Mappings.GET_ONE)
    public MessageInfo getBookToOrderById(@PathVariable Long id) {
        return MessageInfo.success(bookToOrderService.getBookToOrderById(id),
                Arrays.asList("BookToOrderController to order of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public MessageInfo createBookToOrder(@RequestBody @Valid BookToOrderDTO bookToOrderDTO, BindingResult bindingResult) {
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : MessageInfo.success(bookToOrderService.createBookToOrder(bookToOrderDTO),
                Arrays.asList("BookToOrder created succesfully"));
    }

    @PostMapping(Mappings.UPDATE)
    public MessageInfo updateBookToOrder(@PathVariable Long id, @RequestBody @Valid BookToOrderDTO bookToOrderDTO,
                                         BindingResult bindingResult) {
        MessageInfo errors = MessageInfo.getErrors(bindingResult);
        return errors != null ? errors : MessageInfo.success(bookToOrderService.updateBookToOrder(id, bookToOrderDTO),
                Arrays.asList("BookToOrder updated succesfully"));
    }

    @DeleteMapping(Mappings.REMOVE)
    public MessageInfo deleteBookToOrder(@PathVariable Long id) {
        bookToOrderService.deleteBookToOrder(id);
        return MessageInfo.success(null, Arrays.asList("BookToOrder with ID = "+  id.toString() + " removed successfully"));
    }
}
