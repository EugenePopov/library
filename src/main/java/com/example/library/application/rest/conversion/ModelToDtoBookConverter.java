package com.example.library.application.rest.conversion;

import com.example.library.application.rest.dto.BookDto;
import com.example.library.domain.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelToDtoBookConverter implements Converter<Book, BookDto> {

    private final ModelToDtoUserConverter userConverter;

    @Override
    public BookDto convert(Book source) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(source.getAuthor());
        bookDto.setTitle(source.getTitle());
        bookDto.setIsbn(source.getIsbn());
        bookDto.setId(source.getId());
        bookDto.setBorrowedBy(userConverter.convert(source.getBorrowedBy()));
        return bookDto;
    }
}
