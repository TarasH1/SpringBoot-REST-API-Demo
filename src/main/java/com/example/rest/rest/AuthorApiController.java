package com.example.rest.rest;

import com.example.rest.entity.Author;
import com.example.rest.entity.Book;
import com.example.rest.repository.AuthorRepository;

import com.example.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorApiController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Author save(@Validated @RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping(value="/{id}")
    public Author update(@PathVariable Long id, @RequestParam String name, LocalDate birthDate, String phone,
                         String email) {

        Author author = authorRepository.findById(id).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No author with id: " + id));

        author.setAuthorName(name);
        author.setBirthDate(birthDate);
        author.setPhone(phone);
        author.setEmail(email);

        return authorRepository.save(author);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        authorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="/most-successful")
    public Author getMostSuccessfulAuthor() {

        List<Author> authorList = (List<Author>) authorRepository.findAll();
        List<Book> bookList = (List<Book>) bookRepository.findAll();

        return null;
    }

}
