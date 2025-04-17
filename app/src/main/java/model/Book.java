package model;

public class Book {
    public String bookId;
    public String title;
    public String author;
    public String genre;
    public String description;
    public String ownerId;
    public String status;

    public Book() {}

    public Book(String bookId, String title, String author, String genre, String description, String ownerId, String status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.ownerId = ownerId;
        this.status = status;
    }
    public Book(String title, String author, boolean isForSale, boolean isForExchange) {
        this.title = title;
        this.author = author;
        this.status = isForSale ? "for sale" : isForExchange ? "for exchange" : "unlisted";
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }



}

