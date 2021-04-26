package com.example.rest.entity;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String bookName;

    @ManyToOne
    @JoinColumn
    private Author author;

    @Column
    private Long publishedAmount;

    @Column
    private Long soldAmount;

    public Long getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getPublishedAmount() {
        return publishedAmount;
    }

    public void setPublishedAmount(Long publishedAmount) {
        this.publishedAmount = publishedAmount;
    }

    public Long getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Long soldAmount) {
        this.soldAmount = soldAmount;
    }

}
