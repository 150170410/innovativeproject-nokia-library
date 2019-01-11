package com.nokia.library.nokiainnovativeproject.utils;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Constants {

    public static final long MAX_FILE_SIZE = 5242880;

    public  enum MessageTypes {
        BOOK_ALREADY_RENTED,
        BOOK_RESERVED,
        RENTAL_OBSOLETE,
        PROLONG_NOT_AVAILABLE,
        BOOK_ALREADY_HANDED_OVER,
        BOOK_ALREADY_RESERVED,
        NOT_RENTED,
        BOOK_ALREADY_IN_POSSESSION
    }

    public static Map<MessageTypes, String> Messages = new HashMap<>();
    static {
        Messages.put(MessageTypes.BOOK_ALREADY_RENTED, "This book is rented by another user");
        Messages.put(MessageTypes.BOOK_RESERVED, "This book is reserved by another user");
        Messages.put(MessageTypes.RENTAL_OBSOLETE, "This rental is archived.");
        Messages.put(MessageTypes.PROLONG_NOT_AVAILABLE, "This rental cannot be prolonged yet.");
        Messages.put(MessageTypes.BOOK_ALREADY_HANDED_OVER, "This book has already been handed over");
        Messages.put(MessageTypes.BOOK_ALREADY_RESERVED, "This book has already been reserved by this user.");
        Messages.put(MessageTypes.NOT_RENTED, "This book has not been rented by anyone. It's available for renting.");
        Messages.put(MessageTypes.BOOK_ALREADY_IN_POSSESSION, "This book has already been rented by this user. It cannot be reserved.");
    }
}
