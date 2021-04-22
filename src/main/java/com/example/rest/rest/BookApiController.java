package com.example.rest.rest;

import com.example.rest.entity.Author;
import com.example.rest.entity.Book;
import com.example.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/api/book")
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Book saveBook(@Validated @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping(value="/{id}")
    public Book update(@PathVariable Long id, @RequestParam String name, Author author, BigDecimal publishedAmount,
                         BigDecimal soldAmount) throws Exception {

        Book book = bookRepository.findById(id).orElseThrow(()->new Exception("No book with ID : " + id));

        book.setBookName(name);
        book.setAuthor(author);
        book.setPublishedAmount(publishedAmount);
        book.setSoldAmount(soldAmount);

        return book;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
