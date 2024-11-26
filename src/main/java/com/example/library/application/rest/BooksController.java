package com.example.library.application.rest;

import com.example.library.application.rest.conversion.DtoToModelBookConverter;
import com.example.library.application.rest.conversion.ModelToDtoBookConverter;
import com.example.library.application.rest.dto.BookDto;
import com.example.library.domain.model.Book;
import com.example.library.domain.ports.in.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books")
public class BooksController {

    private final BookService bookService;
    private final DtoToModelBookConverter dtoToModelBookConverter;
    private final ModelToDtoBookConverter modelToDtoBookConverter;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBook(@RequestBody final BookDto bookDto) {
        log.info("Received Add Book request");
        Book book = dtoToModelBookConverter.convert(bookDto);
        Book addedBook = bookService.add(book);

        return modelToDtoBookConverter.convert(addedBook);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getBooksBorrowedBy(@RequestParam final String userId) {
        log.info("Received Get Books borrowed by request");
        List<Book> booksBorrowedBy = bookService.findBooksBorrowedBy(UUID.fromString(userId));

        return booksBorrowedBy.stream()
                .map(modelToDtoBookConverter::convert)
                .toList();
    }

    @PostMapping(path = "/loans", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto borrowBook(@RequestBody final BookDto bookDto) {
        log.info("Received Borrow Book request");
        Book borrowedBook = bookService.borrowBook(bookDto.getId(), bookDto.getBorrowedBy().getId());

        return modelToDtoBookConverter.convert(borrowedBook);
    }

    @PostMapping(path = "/releases", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto returnBook(@RequestBody final BookDto bookDto) {
        log.info("Received Return Book request");
        Book borrowedBook = bookService.returnBook(bookDto.getId(), bookDto.getBorrowedBy().getId());

        return modelToDtoBookConverter.convert(borrowedBook);
    }
}
