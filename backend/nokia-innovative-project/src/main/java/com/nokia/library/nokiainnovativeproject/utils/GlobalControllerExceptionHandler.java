package com.nokia.library.nokiainnovativeproject.utils;

import com.nokia.library.nokiainnovativeproject.exceptions.ObjectBindingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun.plugin2.message.Message;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ObjectBindingException.class)
    public MessageInfo handleBindingErrors(ObjectBindingException e){
        List<String> errorsList = e.bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return MessageInfo.failure(errorsList);
    }
}
