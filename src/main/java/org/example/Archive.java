package org.example;

import com.github.javafaker.Faker;
import entities.Book;
import entities.Frequency;
import entities.Magazine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import static entities.Book.getBookSupplier;
import static entities.Magazine.getMagazineSupplier;

public class Archive {

    public static void main(String[] args) {

        Supplier<Book> bookSupplier = getBookSupplier();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookList.add(bookSupplier.get());
        }

        System.out.println("List of book:");
        bookList.forEach(System.out::println);

        System.out.println("-----------------------");

        Supplier<Magazine> magazineSupplier = getMagazineSupplier();
        List<Magazine> magazineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            magazineList.add(magazineSupplier.get());
        }

        System.out.println("List of magazines:");
        magazineList.forEach(System.out::println);

        System.out.println("----------------------------------------------------");

        handleUserActions(bookList, magazineList);
    }

    //   user actions
    public static void handleUserActions(List<Book> bookList, List<Magazine> magazineList) {
        Scanner sc = new Scanner(System.in);
        int handleAction;
        do {
            System.out.println("What type of element do you want to add?");
            System.out.println("1 - Book");
            System.out.println("2 - Magazine");
            System.out.println("3 - Remove based on ISBN");
            System.out.println("0 - Terminate the program.");

            handleAction = sc.nextInt();
            sc.nextLine();

            switch (handleAction) {
                case 1:
                    addBook(bookList);
                    break;
                case 2:
                    addMagazine(magazineList);
                    break;
                case 3:
                    System.out.println("Enter ISBN to remove:");
                    String isbnToRemove = sc.nextLine();
                    removeByIsbn(bookList, magazineList, isbnToRemove);
                case 0:
                    System.out.println("Terminating program... =͟͟͞͞ =͟͟͞͞ ﾍ ( ´ Д `)ﾉ");
                    break;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

        } while (handleAction != 0);
        sc.close();
    }

    //    add element
    public static void addBook(List<Book> bookList) {
        Scanner sc = new Scanner(System.in);
        Faker faker = new Faker();

        String isbn = faker.code().isbn10();

        System.out.println("Title of the book:");
        String title = sc.nextLine();

        System.out.println("Release date of the book:");
        int releaseDate = sc.nextInt();

        System.out.println("Number of pages of the book:");
        int numberOfPages = sc.nextInt();

        sc.nextLine();

        System.out.println("Author of the book:");
        String author = sc.nextLine();

        System.out.println("Genre of the book:");
        String genre = sc.nextLine();

        Book newBook = new Book(isbn, title, releaseDate, numberOfPages, author, genre);
        bookList.add(newBook);

        System.out.println("List of books updated:");
        bookList.forEach(System.out::println);
    }

    public static void addMagazine(List<Magazine> magazineList) {
        Scanner sc = new Scanner(System.in);
        Faker faker = new Faker();

        String isbn = faker.code().isbn10();

        System.out.println("Title of the magazine:");
        String title = sc.nextLine();

        System.out.println("Release date of the magazine:");
        int releaseDate = sc.nextInt();

        System.out.println("Number of pages of the magazine:");
        int numberOfPages = sc.nextInt();

        System.out.println("Select frequency for the magazine:");
        System.out.println("1 - WEEKLY");
        System.out.println("2 - MONTHLY");
        System.out.println("3 - SEMESTER");
        int selectorFrequency = sc.nextInt();
        Frequency[] frequencies = Frequency.values();
        Frequency frequency = frequencies[selectorFrequency - 1];

        Magazine newMagazine = new Magazine(isbn, title, releaseDate, numberOfPages, frequency);
        magazineList.add(newMagazine);

        System.out.println("List of magazines updated:");
        magazineList.forEach(System.out::println);
    }

    // remove element
    public static void removeByIsbn(List<Book> bookList, List<Magazine> magazineList, String isbnInput) {

        bookList.removeIf(book -> book.getISBN().equals(isbnInput));
        magazineList.removeIf(magazine -> magazine.getISBN().equals(isbnInput));
    }


}
