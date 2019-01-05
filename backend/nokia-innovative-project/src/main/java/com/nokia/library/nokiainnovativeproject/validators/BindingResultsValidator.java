package com.nokia.library.nokiainnovativeproject.validators;

import com.nokia.library.nokiainnovativeproject.exceptions.ObjectBindingException;
import org.springframework.validation.BindingResult;

public class BindingResultsValidator {

    public static void validateBindingResults(BindingResult bindingResult, String objectName) throws ObjectBindingException {
        if(bindingResult.hasErrors()){
            throw new ObjectBindingException(objectName, bindingResult);
        }
    }
}

