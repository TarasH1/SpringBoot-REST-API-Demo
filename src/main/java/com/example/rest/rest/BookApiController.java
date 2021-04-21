package com.example.rest.rest;

import com.example.rest.entity.Author;
import com.example.rest.entity.Book;
import com.example.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/book")
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Book saveBook(@Validated @RequestBody Book book) {
        return bookRepository.save(book);
    }
}
