package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookStatus;

import com.nokia.library.nokiainnovativeproject.entities.BookWithOwner;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;

import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.*;
import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final BookStatusRepository bookStatusRepository;
	private final BookStatusService bookStatusService;
	private final BookToOrderService bookToOrderService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final BookOwnerIdRepository bookOwnerIdRepository;

	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAllByOwnersId(userService.getLoggedInUser().getId());
		for (Book book : books) {
			Hibernate.initialize(book.getBookDetails());
			Hibernate.initialize(book.getStatus());
		}
		return books;
	}

	public List<BookWithOwner> getAllBookWithOwner() {
		List<Book> books = getAllBooks();
		List<BookWithOwner> booksWithOwner = new ArrayList<>();
		for(Book book : books) {
			booksWithOwner.add(getBookWithOwner(book));
		}
		return booksWithOwner;
	}

	public BookWithOwner getBookWithOwnerById(Long id) {
		Book book = getBookById(id);
		return getBookWithOwner(book);
	}

	private BookWithOwner getBookWithOwner(Book book) {
		ModelMapper modelMapper = new ModelMapper();
		BookWithOwner bookWithOwner = modelMapper.map(book, BookWithOwner.class);
		bookWithOwner.setActualOwner(userRepository.findById(book.getCurrentOwnerId()).orElseThrow(
				() -> new ResourceNotFoundException("user")));
		return bookWithOwner;
	}

	public Book getBookById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		Hibernate.initialize(book.getBookDetails());
		Hibernate.initialize(book.getStatus());
		return book;
	}

	public List<Book> getAllBooksByBookDetailsId(Long bookDetailsId) {
		return bookRepository.findByBookDetailsId(bookDetailsId);
	}

	public Book createBook(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		book.setAvailableDate(LocalDateTime.now());
		book.setCurrentOwnerId(userService.getLoggedInUser().getId());
		book = assignOwnerToBook(book);
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}
	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setSignature(bookDTO.getSignature());
		List<BookOwnerId> list = new ArrayList<>();
		for(Long ownerId : bookDTO.getOwners()){
			BookOwnerId bookOwnerId = new BookOwnerId();
			bookOwnerId.setOwnerId(ownerId);
			bookOwnerId.setBook(book);
			list.add(bookOwnerId);
		}
		book.setOwnersId(list);
		book.getBookDetails().setIsRemovable(true);
		book.setCurrentOwnerId(userService.getLoggedInUser().getId());
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	private Book assignOwnerToBook(Book book) {
		ArrayList<BookOwnerId> ownersList = new ArrayList<>();
		BookOwnerId bookOwnerId = new BookOwnerId();
		bookOwnerId.setOwnerId(userService.getLoggedInUser().getId());
		ownersList.add(bookOwnerId);
		book.setOwnersId(ownersList);
		return book;
	}

	private Book persistRequiredEntities(Book book, BookDTO bookDTO) {
		Hibernate.initialize(book.getBookDetails());
		Hibernate.initialize(book.getStatus());
		book.setBookDetails(bookDetailsRepository.findById(bookDTO.getBookDetailsId()).orElseThrow(
				() -> new ResourceNotFoundException("book details")));
		book.setStatus(bookStatusRepository.findById(bookDTO.getBookStatusId()).orElseThrow(
				() -> new ResourceNotFoundException("status")));
		book.getBookDetails().setIsRemovable(false);
		BookToOrder bookToOrder = bookToOrderService.getBookToOrderByIsbn(book.getBookDetails().getIsbn());
		if(bookToOrder != null)
			bookToOrderService.acceptBookToOrder(bookToOrder.getId(), book);
		return book;
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));

		if (!book.getStatus().getStatusName().equals("AVAILABLE") && !book.getStatus().getStatusName().equals("UNAVAILABLE"))
			return;
		book.getBookDetails().setIsRemovable(bookRepository.countBooksByBookDetails(book.getBookDetails()) == 1);
		bookRepository.delete(book);
	}

	public Book changeState(Book book, Long newStatusId, Integer days, User newOwner) {
		BookStatus newStatus = bookStatusService.getBookStatusById(newStatusId);
		book.setStatus(newStatus);
		LocalDateTime oldAvailableDate = book.getAvailableDate();
		if (oldAvailableDate == null) {
			oldAvailableDate = LocalDateTime.now();
			book.setAvailableDate(oldAvailableDate);
		}
		if (days == 31) {
			book.setAvailableDate(oldAvailableDate.plusMonths(1));
		} else if (days == -31) {
			book.setAvailableDate(oldAvailableDate.minusMonths(1));
		} else if (0 < days && days < 31) {
			book.setAvailableDate(oldAvailableDate.plusDays(days));
		} else if (-31 < days && days < 0) {
			book.setAvailableDate(oldAvailableDate.minusDays(-1 * days));
		} else if (days == 0) {
			book.setAvailableDate(LocalDateTime.now());
		}
		if (newOwner != null) {
			book.setCurrentOwnerId(newOwner.getId());
		}
		return book;
	}

	public Book lockBook(String signature) {
		Book bookToLock = bookRepository.findBySignature(signature);
		if (!bookToLock.getStatus().getId().equals(BookStatusEnum.AVAILABLE.getId())) {
			throw new InvalidBookStateException(Constants.MessageTypes.BOOK_RESERVED);
		}
		return changeState(bookToLock, BookStatusEnum.UNAVAILABLE.getId(), 0, null);
	}

	public Book unlockBook(String signature) {
		Book bookToUnlock = bookRepository.findBySignature(signature);
		if (!bookToUnlock.getStatus().getId().equals(BookStatusEnum.UNAVAILABLE.getId())) {
			throw new InvalidBookStateException(Constants.MessageTypes.BOOK_RESERVED);
		}
		return changeState(bookToUnlock, BookStatusEnum.AVAILABLE.getId(), 0, null);
	}

	private void validateNewOwner(Long newOwnerId, User loggedUser) {
        User newOwner = userRepository.findById(newOwnerId).orElseThrow(() -> new ResourceNotFoundException("user"));
        if (loggedUser.getId() == newOwner.getId()) {
            throw new ValidationException(Messages.get(Constants.MessageTypes.CANT_ASSIGN_TO_YOURSELF));
        }
        // new books user need to be admin
        if (!isAdmin(newOwner)) {
            throw new ValidationException(Messages.get(Constants.MessageTypes.USER_IS_NO_ADMIN));
        }
    }

    @Transactional
	public List<Book> addNewOwnerToBooks(Long newOwnerId) {
        User loggedUser = userService.getLoggedInUser();
        validateNewOwner(newOwnerId, loggedUser);

        List<Book> loggedUserBooks = bookRepository.findAllByOwnersId(loggedUser.getId());
        List<Long> iterable = new LinkedList<>();
        List<BookOwnerId> newOwnersId = new ArrayList<>();
        for (Book book : loggedUserBooks) {
            boolean isOwner = false;
            for (BookOwnerId owner : book.getOwnersId()) {
                if (owner.getOwnerId() == newOwnerId)
                    isOwner = true;
            }
            if (!isOwner) {
                BookOwnerId bookOwnerId = new BookOwnerId();
                bookOwnerId.setOwnerId(newOwnerId);
                bookOwnerId.setBook(book);
                newOwnersId.add(bookOwnerId);
                iterable.add(book.getId());
            }
        }
        bookOwnerIdRepository.saveAll(newOwnersId);
        return bookRepository.findAllByOwnersId(loggedUser.getId());
    }

    @Transactional
	public List<Book> transferAllBookToNewOwner(Long newOwnerId) {
        User loggedUser = userService.getLoggedInUser();
        validateNewOwner(newOwnerId, loggedUser);

        List<Book> loggedUserBooks = bookRepository.findAllByOwnersId(loggedUser.getId());
        List<Book> booksToSave = new LinkedList<>();
        List<BookOwnerId> booksOwnersIdToDelete = new LinkedList<>();

        for (Book book : loggedUserBooks) {
            boolean isOwner = false;
            BookOwnerId ownerToDelete = null;
            Book bookToSave = book;
            for (BookOwnerId owner : book.getOwnersId()) {
                if (owner.getOwnerId() == newOwnerId)
                    isOwner = true;

                if(owner.getOwnerId() == loggedUser.getId()) {
                    ownerToDelete = owner;
                }
            }
            if (!isOwner) {
                BookOwnerId bookOwnerId = new BookOwnerId();
                bookOwnerId.setOwnerId(newOwnerId);
                bookOwnerId.setBook(book);
                List<BookOwnerId> listWithOwner = bookToSave.getOwnersId();
                listWithOwner.add(bookOwnerId);
                if(ownerToDelete != null) {
                    listWithOwner.remove(ownerToDelete);
                    booksOwnersIdToDelete.add(ownerToDelete);
                }
                bookToSave.setOwnersId(listWithOwner);
                bookToSave.setCurrentOwnerId(newOwnerId);
            } else if(ownerToDelete != null) {
                List<BookOwnerId> listWithOwner = bookToSave.getOwnersId();
                listWithOwner.remove(ownerToDelete);
                bookToSave.setOwnersId(listWithOwner);
                bookToSave.setCurrentOwnerId(newOwnerId);
            }
            booksToSave.add(bookToSave);
        }
        bookRepository.saveAll(booksToSave);
        bookOwnerIdRepository.deleteAll(booksOwnersIdToDelete);

        return bookRepository.findAllByOwnersId(userService.getLoggedInUser().getId());
	}

	private boolean isAdmin(User user) {
		List <Role> roles = user.getRoles();
		boolean isAdmin = false;
		for(Role role : roles) {
			if(role.getRole().equals("ROLE_ADMIN")) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}
}