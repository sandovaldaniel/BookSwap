package model;

public class Book {
    public String bookId;
    public String title;
    public String author;
    public String genre;
    public String ownerEmail;

    public boolean isAvailableToBorrow;
    public boolean isAvailableToExchange;
    public boolean isAvailableToSell;

    public Book() {}

    public Book(String bookId, String title, String author, String genre,
                String ownerEmail, boolean isAvailableToBorrow,
                boolean isAvailableToExchange, boolean isAvailableToSell) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ownerEmail = ownerEmail;
        this.isAvailableToBorrow = isAvailableToBorrow;
        this.isAvailableToExchange = isAvailableToExchange;
        this.isAvailableToSell = isAvailableToSell;
    }

}

