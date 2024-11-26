package com.example.library.domain.ports.out;

import com.example.library.domain.model.Book;
import com.example.library.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(UUID id);

    List<Book> findAllBorrowedBy(UUID userId);

    Book borrowBook(UUID bookId, User user);
}
