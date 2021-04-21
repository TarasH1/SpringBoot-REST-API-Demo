package com.example.rest.rest;

import com.example.rest.entity.Author;
import com.example.rest.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorApiController {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public Author save(@Validated @RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping(value="/{id}")
    public Author update(@PathVariable Long id, @RequestParam String name, LocalDate birthDate, String phone,
                         String email) throws Exception {

        Author author = authorRepository.findById(id).orElseThrow(()->new Exception("No author with ID : " + id));

        author.setAuthorName(name);
        author.setBirthDate(birthDate);
        author.setPhone(phone);
        author.setEmail(email);

        return author;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        authorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
