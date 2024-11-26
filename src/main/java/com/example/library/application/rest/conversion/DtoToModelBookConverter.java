package com.example.library.application.rest.conversion;

import com.example.library.application.rest.dto.BookDto;
import com.example.library.domain.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DtoToModelBookConverter implements Converter<BookDto, Book> {

    private final DtoToModelUserConverter dtoToModelUserConverter;

    @Override
    public Book convert(BookDto source) {
        Book book = new Book();
        book.setTitle(source.getTitle());
        book.setAuthor(source.getAuthor());
        book.setIsbn(source.getIsbn());
        book.setBorrowedBy(dtoToModelUserConverter.convert(source.getBorrowedBy()));
        return book;
    }
}
