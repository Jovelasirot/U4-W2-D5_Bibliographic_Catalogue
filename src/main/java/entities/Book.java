package entities;

import com.github.javafaker.Faker;

import java.util.Random;
import java.util.function.Supplier;

public class Book extends Catalog {
    private String author;
    private String genre;

    public Book(String ISBN, String title, int releaseDate, int numberPages, String author, String genre) {
        super(ISBN, title, releaseDate, numberPages);
        this.author = author;
        this.genre = genre;
    }

    public static Supplier<Book> getBookSupplier() {
        Random rdm = new Random();
        Faker faker = new Faker();

        return () -> {
            String isbn = faker.code().isbn10();
            String title = faker.book().title();
            int releaseDate = rdm.nextInt(1950, 2024);
            int numberPage = rdm.nextInt(20, 500);
            String author = faker.book().author();
            String genre = faker.book().genre();

            return new Book(isbn, title, releaseDate, numberPage, author, genre);
        };
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + getISBN() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", numberPages='" + getNumberPages() + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
