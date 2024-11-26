package com.example.library.domain.adapters;

import com.example.library.domain.exceptions.BookNotFoundException;
import com.example.library.domain.exceptions.UserMismatchException;
import com.example.library.domain.exceptions.UserNotFoundException;
import com.example.library.domain.model.Book;
import com.example.library.domain.model.User;
import com.example.library.domain.ports.in.BookService;
import com.example.library.domain.ports.out.BookRepository;
import com.example.library.domain.ports.out.UserRepository;
import com.example.library.infrastructure.dal.mongo.repository.MongoBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceAdapter implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MongoBookRepository mongoBookRepository;

    @Override
    public Book add(Book book) {
        log.info("Processing Add Book request: {}", book);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBooksBorrowedBy(UUID userId) {
        log.info("Processing Find Borrowed Books request for user: {}", userId);

        return bookRepository.findAllBorrowedBy(userId);
    }

    @Override
    @Transactional
    public Book borrowBook(UUID bookId, UUID userId) {
        log.info("Processing Borrow Book request for user: {}", userId);

        if (bookRepository.findById(bookId).isEmpty()) {
            log.info("Could not find book by Id: {}", bookId);
            throw new BookNotFoundException();
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            log.info("Could not find user by Id: {}", userId);
            throw new UserNotFoundException();
        } else {
            Book borrowedBook = bookRepository.borrowBook(bookId, optionalUser.get());
            log.info("Successfully Borrowed book: {}", borrowedBook);
            return borrowedBook;
        }
    }

    @Override
    public Book returnBook(UUID bookId, UUID userId) {
        log.info("Processing Return Book request for user: {}", userId);

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            log.info("Could not find book by Id: {}", bookId);
            throw new BookNotFoundException();

        } else {
            Book book = bookOptional.get();
            if (book.getBorrowedBy() == null) {
                log.warn("Book released silently {}", book);
                return book;
            } else {
                if (book.getBorrowedBy().getId() == userId) {
                    book.setBorrowedBy(null);
                    Book returnedBook = mongoBookRepository.save(book);
                    log.info("Successfully Returned book: {}", returnedBook);
                    return returnedBook;
                } else {
                    log.error("Book borrowed by {} but released by {}", book.getBorrowedBy(), userId);
                    throw new UserMismatchException();
                }
            }
        }
    }
}
