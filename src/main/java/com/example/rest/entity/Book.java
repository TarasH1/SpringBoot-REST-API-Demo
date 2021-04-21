package com.example.rest.entity;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private BigDecimal publishedAmount;

    @Column
    private BigDecimal soldAmount;

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
