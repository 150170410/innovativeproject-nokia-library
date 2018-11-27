package com.nokia.library.nokiainnovativeproject.validators;

import com.nokia.library.nokiainnovativeproject.entities.PictureUpload;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PictureValidator implements Validator {
    public boolean supports(Class clazz) {
        return PictureUpload.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        //TODO Add type validation
    }
    }
