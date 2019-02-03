package com.nokia.library.nokiainnovativeproject.utils;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Constants {

    public static final long MAX_FILE_SIZE = 5242880;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ADMIN = "ADMIN";
    public static final String EMPLOYEE = "EMPLOYEE";

    public static final String REST_AUTOCOMPLETION_CLIENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    public static final String IT_BOOK_STORE_API_LINK = "https://api.itbook.store/1.0/books/";
    public static final String GOOGLE_API_LINK = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    public static final String BN_API_LINK = "http://data.bn.org.pl/api/bibs.json?isbnIssn=";

    public enum MessageTypes {
        BOOK_ALREADY_RENTED,
        BOOK_RESERVED,
        BOOK_WITH_ISBN_NOT_FOUND,
        RENTAL_OBSOLETE,
        PROLONG_NOT_AVAILABLE,
        BOOK_ALREADY_HANDED_OVER,
        BOOK_ALREADY_RESERVED,
        NOT_RENTED,
        BOOK_ALREADY_IN_POSSESSION,
        CREATED_SUCCESSFULLY,
		BORROWED_SUCCESSFULLY,
		PROLONGED_SUCCESSFULLY,
		HANDOVER_SUCCESSFULLY,
        UPDATED_SUCCESSFULLY,
        REMOVED_SUCCESSFULLY,
        LOCKED_SUCCESSFULLY,
        UNLOCKED_SUCCESSFULLY,
        RETURNED_SUCCESSFULLY,
        ACCEPTED_SUCCESSFULLY,
        CANCELLED_SUCCESSFULLY,
        REJECTED_SUCCESSFULLY,
		RESERVED_SUCCESSFULLY,
        SENT_SUCCESSFULLY,
        LIST_OF,
        REQUESTED,
        UPLOADED,
        USER_LOGGED_IN,
        USER_NOT_LOGGED_IN,
        ADMIN_ROLE_ADDED,
        ADMIN_ROLE_REMOVED,
        USER_ACCOUNT_LOCKED,
        USER_ACCOUNT_UNLOCKED,
        ACCESS_DENIED,
        CANT_FIND,
        INVALID_TYPE,
        IS_ASSIGNED_CANT_DELETE,
        IS_EMPTY,
        USER_HAS_NO_ADDRESS,
        USER_WITH_EMAIL_EXIST,
        CANT_DELETE_LAST_ADMIN,
        SPECIFY_ADDRESS,
        CANT_FIND_USER_BY_EMAIL,
        CANT_ASSIGN_TO_YOURSELF,
        USER_IS_NO_ADMIN,
        STILL_HAS_BOOKS_CANT_DEMOTE,
        HAS_BEEN_ASSIGNED_TO_ADMIN,
        HAS_BEEN_TRANSFERRED_TO_ADMIN
    }

    public static Map<MessageTypes, String> Messages = new HashMap<>();
    static {

        Messages.put(MessageTypes.CREATED_SUCCESSFULLY, " created successfully.");
        Messages.put(MessageTypes.UPDATED_SUCCESSFULLY, " updated successfully.");
        Messages.put(MessageTypes.REMOVED_SUCCESSFULLY, " you have selected has been removed successfully.");
        Messages.put(MessageTypes.LIST_OF, "List of ");
        Messages.put(MessageTypes.REQUESTED, " you are requesting.");
        Messages.put(MessageTypes.BOOK_WITH_ISBN_NOT_FOUND, "Book details with this isbn not found.");
        Messages.put(MessageTypes.LOCKED_SUCCESSFULLY, " locked successfully.");
        Messages.put(MessageTypes.UNLOCKED_SUCCESSFULLY, " unlocked successfully");
        Messages.put(MessageTypes.SENT_SUCCESSFULLY, " sent successfully.");
        Messages.put(MessageTypes.UPLOADED, " uploaded.");


        Messages.put(MessageTypes.ADMIN_ROLE_ADDED, "The admin role has been successfully added.");
        Messages.put(MessageTypes.ADMIN_ROLE_REMOVED, "The admin role has been successfully removed.");
        Messages.put(MessageTypes.USER_ACCOUNT_LOCKED, "The user account has been successfully locked");
        Messages.put(MessageTypes.USER_ACCOUNT_UNLOCKED, "The user account has been successfully unlocked");
        Messages.put(MessageTypes.USER_LOGGED_IN, "You are logged in.");
        Messages.put(MessageTypes.USER_NOT_LOGGED_IN, "Please, log in!");
        Messages.put(MessageTypes.ACCESS_DENIED, "Access denied!");
        Messages.put(MessageTypes.CANT_FIND, "Sorry, but we can't find this ");
        Messages.put(MessageTypes.INVALID_TYPE, "Invalid file type. Supported file types: ");
        Messages.put(MessageTypes.IS_ASSIGNED_CANT_DELETE, " you are trying to delete is assigned to a book. You can't delete it.");
        Messages.put(MessageTypes.IS_EMPTY, " is either null or empty.");
        Messages.put(MessageTypes.USER_HAS_NO_ADDRESS, "User has no address. First, assign the address to the user.");
        Messages.put(MessageTypes.USER_WITH_EMAIL_EXIST, "User with this email already exist!");
        Messages.put(MessageTypes.CANT_DELETE_LAST_ADMIN, "You can't delete the last admin from the database!");
        Messages.put(MessageTypes.SPECIFY_ADDRESS, "Please. Specify address!");
        Messages.put(MessageTypes.CANT_FIND_USER_BY_EMAIL, "Sorry we can't find this user by email!");
        Messages.put(MessageTypes.CANT_ASSIGN_TO_YOURSELF, "You are trying to assign your books to yourself. Choose a different admin!");
        Messages.put(MessageTypes.USER_IS_NO_ADMIN, "New books owner need to have admin role!");
        Messages.put(MessageTypes.STILL_HAS_BOOKS_CANT_DEMOTE, "This admin still has books. Transport his books to another admin.");
        Messages.put(MessageTypes.HAS_BEEN_ASSIGNED_TO_ADMIN, " have been assigned to the new admin");
        Messages.put(MessageTypes.HAS_BEEN_TRANSFERRED_TO_ADMIN, " have been transferred to the new admin");

        // rentals
		Messages.put(MessageTypes.RETURNED_SUCCESSFULLY, "Book returned successfully.");
		Messages.put(MessageTypes.BORROWED_SUCCESSFULLY, "Book borrowed successfully, please check email for pick up details.");
		Messages.put(MessageTypes.PROLONGED_SUCCESSFULLY, "Book successfully prolonged for 1 month.");
		Messages.put(MessageTypes.HANDOVER_SUCCESSFULLY, "Book handed over successfully.");
		Messages.put(MessageTypes.PROLONG_NOT_AVAILABLE, "This rental cannot be prolonged yet.");
		Messages.put(MessageTypes.RENTAL_OBSOLETE, "This rental is archived.");
		Messages.put(MessageTypes.BOOK_ALREADY_RENTED, "This book is rented by another user");
		Messages.put(MessageTypes.BOOK_ALREADY_HANDED_OVER, "This book has already been handed over");
		Messages.put(MessageTypes.BOOK_ALREADY_RESERVED, "You already have reservation for this book.");

		// reservations
		Messages.put(MessageTypes.RESERVED_SUCCESSFULLY, "Book reserved successfully.");
		Messages.put(MessageTypes.ACCEPTED_SUCCESSFULLY, " accepted successfully.");
		Messages.put(MessageTypes.CANCELLED_SUCCESSFULLY, " cancelled successfully.");
		Messages.put(MessageTypes.REJECTED_SUCCESSFULLY, " rejected successfully.");
		Messages.put(MessageTypes.BOOK_RESERVED, "This book is reserved by another user");
		Messages.put(MessageTypes.NOT_RENTED, "This book has not been rented by anyone. It's available for renting.");
		Messages.put(MessageTypes.BOOK_ALREADY_IN_POSSESSION, "This book has already been rented by this user. It cannot be reserved.");
    }
}