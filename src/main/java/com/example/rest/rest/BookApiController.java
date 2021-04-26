package com.example.rest.rest;

import com.example.rest.entity.Author;
import com.example.rest.entity.Book;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/book")
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public static class BookRequestBody {

        private String bookName;
        private Long authorId;
        private BigDecimal publishedAmount;
        private BigDecimal soldAmount;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public Long getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Long authorId) {
            this.authorId = authorId;
        }

        public BigDecimal getPublishedAmount() {
            return publishedAmount;
        }

        public void setPublishedAmount(BigDecimal publishedAmount) {
            this.publishedAmount = publishedAmount;
        }

        public BigDecimal getSoldAmount() {
            return soldAmount;
        }

        public void setSoldAmount(BigDecimal soldAmount) {
            this.soldAmount = soldAmount;
        }
    }

    @PostMapping
    public Book saveBook(@RequestBody BookRequestBody body) {
        Long authorId = body.authorId;
        Author author = authorRepository.findById(authorId).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No author with id: " + authorId));

        Book book = new Book();

        book.setBookName(body.bookName);
        book.setAuthor(author);
        book.setPublishedAmount(body.publishedAmount);
        book.setSoldAmount(body.soldAmount);

        return bookRepository.save(book);
    }

    @PutMapping(value="/{id}")
    public Book update(@PathVariable Long id, @RequestBody BookRequestBody body) {

        Book book = bookRepository.findById(id).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No book with id: " + id));
        Long authorId = body.authorId;
        Author author = authorRepository.findById(authorId).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No author with id: " + authorId));

        book.setBookName(body.getBookName());
        book.setAuthor(author);
        book.setPublishedAmount(body.getPublishedAmount());
        book.setSoldAmount(body.getSoldAmount());

        return bookRepository.save(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="/author")
    public List<Book> getBookListByAuthorName(@RequestParam String name) {

            List<Book> bookList = (List<Book>) bookRepository.findAll();

            bookList = bookList.stream().filter(book -> book.getAuthor().getAuthorName().equals(name))
                    .collect(Collectors.toList());

            if (bookList.size() > 0) {
                return bookList;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Books not found by author name: " + name);
            }

    }

    @GetMapping(value="/author/sold")
    public Book getMaxSoldAmountByAuthorName(@RequestParam String name) {

        List<Book> bookList = (List<Book>) bookRepository.findAll();

        bookList = bookList.stream().filter(book -> book.getAuthor().getAuthorName().equals(name))
                .collect(Collectors.toList());

        Book maxByAuthor = null;

        if (bookList.size() > 0) {

            maxByAuthor = bookList
                    .stream()
                    .max(Comparator.comparing(Book::getSoldAmount))
                    .orElseThrow(NoSuchElementException::new);
        }

        if (maxByAuthor != null) {
            return maxByAuthor;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found");
        }

    }

    @GetMapping(value="/author/published")
    public Book getMaxPublishedAmountByAuthorName(@RequestParam String name) {

        List<Book> bookList = (List<Book>) bookRepository.findAll();

        bookList = bookList.stream().filter(book -> book.getAuthor().getAuthorName().equals(name))
                .collect(Collectors.toList());

        Book maxByAuthor = null;

        if (bookList.size() > 0) {

            maxByAuthor = bookList
                    .stream()
                    .max(Comparator.comparing(Book::getPublishedAmount))
                    .orElseThrow(NoSuchElementException::new);
        }

        if (maxByAuthor != null) {
            return maxByAuthor;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found");
        }

    }

}
