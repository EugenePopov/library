package com.example.library.infrastructure.dal.mongo.repository;

import com.example.library.domain.exceptions.BookUnavailableException;
import com.example.library.domain.model.Book;
import com.example.library.domain.model.User;
import com.example.library.domain.ports.out.BookRepository;
import com.example.library.infrastructure.conversion.ModelToEntityBookConverter;
import com.example.library.infrastructure.conversion.ModelToEntityUserConverter;
import com.example.library.infrastructure.conversion.MongoEntityToModelBookConverter;
import com.example.library.infrastructure.dal.mongo.entity.MongoBookEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class MongoBookRepository implements BookRepository {

    private final ModelToEntityBookConverter bookConverter;
    private final ModelToEntityUserConverter userConverter;
    private final MongoEntityToModelBookConverter mongoEntityBookConverter;
    private final SpringDataMongoBookRepository mongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Book> findById(UUID id) {
        Optional<MongoBookEntity> optionalMongoBookEntity = mongoRepository.findById(id);

        return optionalMongoBookEntity
                .map(mongoEntityBookConverter::convert);
    }

    @Override
    public Book save(Book book) {
        MongoBookEntity bookEntity = bookConverter.convert(book);
        MongoBookEntity savedBookEntity = mongoRepository.save(bookEntity);

        log.info("Saved book: {}", savedBookEntity);

        return mongoEntityBookConverter.convert(savedBookEntity);
    }

    @Override
    public List<Book> findAllBorrowedBy(UUID userId) {
        List<MongoBookEntity> bookEntities = mongoRepository.findByBorrowedById(userId);

        List<Book> books = bookEntities.stream()
                .map(mongoEntityBookConverter::convert)
                .toList();

        log.info("Found {} books borrowed by user {}", bookEntities.size(), userId);
        return books;
    }

    @Override
    public Book borrowBook(UUID bookId, User user) {
        Query query = new Query(Criteria
                .where("id")
                .is(bookId)
                .and("borrowedBy.id")
                .is(null));

        Update update = new Update().set("borrowedBy", userConverter.convert(user));

        MongoBookEntity borrowedBook = mongoTemplate.findAndModify(query, update, MongoBookEntity.class);

        if (borrowedBook == null) {
            throw new BookUnavailableException();
        }

        log.info("Borrowed book: {}", borrowedBook);

        return mongoEntityBookConverter.convert(borrowedBook);
    }
}
