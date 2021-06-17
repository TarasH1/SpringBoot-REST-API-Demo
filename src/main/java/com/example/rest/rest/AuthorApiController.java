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
    public Map.Entry<String, Double> getMostSuccessfulAuthor() {

        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Author> authorList = (List<Author>) authorRepository.findAll();

        HashMap<String, Double> authorFinalList = new HashMap<>();
        double successRate = 0;

        for (Book book : bookList) {
            Author author = book.getAuthor();

            double successBookRateSum = 0;

            for (Author _author : authorList) {

                if (author.equals(_author)) {
                    List<Book> booksByAuthor = bookRepository.findByAuthorId(_author.getId());

                    for (Book b : booksByAuthor) {
                        successBookRateSum = successBookRateSum + b.getSuccessBookRate();
                    }
                    successRate = successBookRateSum / (double) booksByAuthor.size();
                    authorFinalList.put(_author.getAuthorName(), successRate);
                }
            }
        }

        Map.Entry<String, Double> maxEntry = null;
        for (Map.Entry<String, Double> entry : authorFinalList.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

/*        authorFinalList.values()
                .stream()
                .max(Comparator.comparing())
                //.mapToDouble(v -> v)
                .orElseThrow(NoSuchElementException::new);*/

        if (bookList.size() > 0) {
            return maxEntry;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Author not found");
        }
    }

}
