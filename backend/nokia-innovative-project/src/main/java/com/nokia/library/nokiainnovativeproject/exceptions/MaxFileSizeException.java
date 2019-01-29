package com.nokia.library.nokiainnovativeproject.exceptions;

import com.nokia.library.nokiainnovativeproject.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxFileSizeException extends RuntimeException{

    public MaxFileSizeException(long size){
        super(String.format("The request was rejected because its size (%s) exceeds the configured maximum (%s)", size, Constants.MAX_FILE_SIZE));
    }
}
