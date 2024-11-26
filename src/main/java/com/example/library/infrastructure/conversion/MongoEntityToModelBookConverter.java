package com.example.library.infrastructure.conversion;

import com.example.library.domain.model.Book;
import com.example.library.domain.model.User;
import com.example.library.infrastructure.dal.mongo.entity.MongoBookEntity;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MongoEntityToModelBookConverter implements Converter<MongoBookEntity, Book> {

    private final MongoEntityToModelUserConverter userConverter;

    @Override
    public Book convert(MongoBookEntity source) {
        Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setAuthor(source.getAuthor());

        User user = userConverter.convert(source.getBorrowedBy());
        book.setBorrowedBy(user);

        return book;
    }
}
