package com.example.rest.repository;

import com.example.rest.entity.Author;
import com.example.rest.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByAuthorNameContains(String name);
}
