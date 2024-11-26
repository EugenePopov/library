package com.example.library.application.rest.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookDto {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private UserDto borrowedBy;
}
