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
import java.util.*;
import java.util.stream.Collectors;

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
    public HashMap<String, String> getMostSuccessfulAuthor() {

        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Author> authorList = (List<Author>) authorRepository.findAll();

        for (Book book : bookList) {
            Author author = book.getAuthor();

            Long authorId = null;
            for (Author _author : authorList) {
                authorId = _author.getId();

                if (author.getId().equals(authorId)) {
                    List<Book> booksByAuthor = bookRepository.findAllByAuthorId(authorId);
                    double successRate = book.getSuccessBookRate() / (double) booksByAuthor.size();
                    System.out.println("successAuthorRate for author: " + author.getAuthorName() + " " + successRate);
                }
            }

        }

        Double successAuthorRate = null;

        for (Book book : bookList) {
            Double successBookRate = book.getSuccessBookRate();
            Double allBooksNumber = (double) bookList.size();
            successAuthorRate = successBookRate / allBooksNumber;
            //System.out.println(successAuthorRate);
        }

        Double finalSuccessAuthorRate = successAuthorRate;
        bookList
                .stream()
                .mapToDouble(b -> finalSuccessAuthorRate)
                .max()
                .orElseThrow(NoSuchElementException::new);

        Author author = null;
        for (Book book : bookList) {
            author = book.getAuthor();
        }

        LinkedHashMap<String, String> _authorList = new LinkedHashMap<>();

        _authorList.put("authorName", author.getAuthorName());
        _authorList.put("successAuthorRate", finalSuccessAuthorRate.toString());

        if (bookList.size() > 0) {
            return _authorList;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Author not found");
        }
    }

}
