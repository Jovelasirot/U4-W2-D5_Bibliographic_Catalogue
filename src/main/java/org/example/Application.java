package org.example;

import entities.Book;
import entities.Magazine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static entities.Book.getBookSupplier;
import static entities.Magazine.getMagazineSupplier;

public class Application {

    public static void main(String[] args) {

        Supplier<Book> bookSupplier = getBookSupplier();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookList.add(bookSupplier.get());
        }

        System.out.println("List of book:");
        bookList.forEach(System.out::println);

        Supplier<Magazine> magazineSupplier = getMagazineSupplier();
        List<Magazine> magazineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            magazineList.add(magazineSupplier.get());
        }

        System.out.println("List of magazines:");
        magazineList.forEach(System.out::println);
    }


}
