package com.example.library.domain.ports.in;

import com.example.library.domain.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Book add(Book book);

    List<Book> findBooksBorrowedBy(UUID userId);

    Book borrowBook(UUID bookId, UUID userId);

    Book returnBook(UUID bookId, UUID userId);

}
