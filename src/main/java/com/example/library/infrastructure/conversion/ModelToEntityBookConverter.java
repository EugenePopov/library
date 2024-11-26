package com.example.library.infrastructure.conversion;

import com.example.library.domain.model.Book;
import com.example.library.infrastructure.dal.mongo.entity.MongoBookEntity;
import com.example.library.infrastructure.dal.mongo.entity.MongoUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelToEntityBookConverter implements Converter<Book, MongoBookEntity> {

    private ModelToEntityUserConverter userConverter;

    @Override
    public MongoBookEntity convert(Book source) {
        MongoBookEntity bookEntity = new MongoBookEntity();
        bookEntity.setId(source.getId());
        bookEntity.setIsbn(source.getIsbn());
        bookEntity.setAuthor(source.getAuthor());
        bookEntity.setTitle(source.getTitle());

        MongoUserEntity userEntity = userConverter.convert(source.getBorrowedBy());

        bookEntity.setBorrowedBy(userEntity);

        return bookEntity;
    }
}
