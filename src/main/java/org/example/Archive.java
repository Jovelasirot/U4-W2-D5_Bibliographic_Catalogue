package org.example;

import com.github.javafaker.Faker;
import entities.Book;
import entities.Catalog;
import entities.Frequency;
import entities.Magazine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
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

//        System.out.println("List of book:");
//        bookList.forEach(System.out::println);

//        System.out.println("-----------------------");

        Supplier<Magazine> magazineSupplier = getMagazineSupplier();
        List<Magazine> magazineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            magazineList.add(magazineSupplier.get());
        }

//        System.out.println("List of magazines:");
//        magazineList.forEach(System.out::println);

//        System.out.println("----------------------------------------------------");

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.addAll(bookList);
        catalogList.addAll(magazineList);

        handleUserActions(bookList, magazineList, catalogList);
    }

    //   user actions
    public static void handleUserActions(List<Book> bookList, List<Magazine> magazineList, List<Catalog> catalogList) {
        Scanner sc = new Scanner(System.in);
        int handleAction;
        do {
            try {

                System.out.println("What do you want to do?");
                System.out.println("1 - Add book");
                System.out.println("2 - Add magazine");
                System.out.println("3 - Remove based on ISBN");
                System.out.println("4 - View book list");
                System.out.println("5 - View magazine list");
                System.out.println("6 - Search by year");
                System.out.println("7 - Search a book by author");
                System.out.println("8 - Save data on disc");
                System.out.println("9 - Read a file from disc");
                System.out.println("10 - View the entire catalog");
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
                        break;

                    case 4:
                        System.out.println("Current book list:");
                        bookList.forEach(System.out::println);
                        break;

                    case 5:
                        System.out.println("Current magazine list:");
                        magazineList.forEach(System.out::println);
                        break;

                    case 6:
                        System.out.println("Enter a year:");
                        int yearToSearch = sc.nextInt();
                        System.out.println(searchByReleaseDate(bookList, magazineList, yearToSearch));
                        break;

                    case 7:
                        System.out.println("Enter the name of the Author:");
                        String authorToSearch = sc.nextLine();
                        System.out.println(searchByAuthor(bookList, authorToSearch));
                        break;

                    case 8:
                        System.out.println("What type do want to save:");
                        System.out.println("1 - Books");
                        System.out.println("2 - Magazine");
                        int typeOfData = sc.nextInt();

                        sc.nextLine();

                        System.out.println("Save file as:");
                        String nameFile = sc.nextLine();
                        String filePath = "src/main/data/" + nameFile + ".txt";

                        saveElementsOnDisc(bookList, magazineList, typeOfData, filePath);
                        break;

                    case 9:
                        handleLoadFromDisk();
                        break;

                    case 10:
                        System.out.println("The entire catalog:");
                        catalogList.forEach(System.out::println);
                        System.out.println("---------------------------");
                        break;

                    case 0:
                        System.out.println("Terminating program... =͟͟͞͞ =͟͟͞͞ ﾍ ( ´ Д `)ﾉ");
                        break;

                    default:
                        System.out.println("Invalid action. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, type a number.");
                System.out.println("-------------");
                handleAction = -1;
                sc.nextLine();
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
        System.out.println("-------------------------");
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
        System.out.println("-------------------------");
    }

    // remove element
    public static void removeByIsbn(List<Book> bookList, List<Magazine> magazineList, String isbnInput) {

        bookList.removeIf(book -> book.getISBN().equals(isbnInput));
        magazineList.removeIf(magazine -> magazine.getISBN().equals(isbnInput));
    }

    //    search by release year
    public static List<Catalog> searchByReleaseDate(List<Book> bookList, List<Magazine> magazineList, int yearInput) {
        List<Catalog> results = new ArrayList<>();

        results.addAll(bookList.stream().filter(book -> book.getReleaseDate() == yearInput).toList());

        results.addAll(magazineList.stream().filter(magazine -> magazine.getReleaseDate() == yearInput).toList());

        return results;
    }

    //    search by author
    public static List<Catalog> searchByAuthor(List<Book> bookList, String authorInput) {
        List<Catalog> results = new ArrayList<>();

        results.addAll(bookList.stream().filter(book -> book.getAuthor().equals(authorInput)).toList());


        return results;
    }

    //    save data
    public static void saveElementsOnDisc(List<Book> bookList, List<Magazine> magazineList, int typeOfData, String filePath) {
        File file = new File(filePath);
        String dataToWrite = "";

        switch (typeOfData) {

            case 1:
                for (Book book : bookList) {
                    dataToWrite += book.getISBN() + "☝"
                            + book.getTitle() + "☝"
                            + book.getReleaseDate() + "☝"
                            + book.getNumberPages() + "☝"
                            + book.getAuthor() + "☝"
                            + book.getGenre()
                            + "◥█̆̈◤࿉∥";
                }
                break;

            case 2:
                for (Magazine magazine : magazineList) {
                    dataToWrite += magazine.getISBN() + "☝"
                            + magazine.getTitle() + "☝"
                            + magazine.getReleaseDate() + "☝"
                            + magazine.getNumberPages() + "☝"
                            + magazine.getFrequency()
                            + "◥█̆̈◤࿉∥";

                }
                break;

            default:
                System.out.println("Invalid type of data");
        }

        try {
            FileUtils.writeStringToFile(file, dataToWrite.toString(), "UTF-8");
            System.out.println("Data saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //read data
    public static void handleLoadFromDisk() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do You want to load book (B) or magazine (M)");

        String dataFileInput = sc.nextLine();

        if (Objects.equals(dataFileInput, "B")) {
            System.out.println("Insert the name of the file(Book) you want to load:");
            String nameFileToRead = sc.nextLine();

            String filePathToRead = "src/main/data/" + nameFileToRead + ".txt";

            try {
                System.out.println("Data from file: " + nameFileToRead);
                loadBooksFromDisk(filePathToRead).forEach(System.out::println);
                System.out.println("---------------------------");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (Objects.equals(dataFileInput, "M")) {
            System.out.println("Insert the name of the file(Magazine) you want to load:");
            String nameFileToRead = sc.nextLine();

            String filePathToRead = "src/main/data/" + nameFileToRead + ".txt";

            try {
                System.out.println("Data from file: " + nameFileToRead);
                loadMagazineFromDisk(filePathToRead).forEach(System.out::println);
                System.out.println("---------------------------");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid input.");
        }
    }


    public static List<Book> loadBooksFromDisk(String filePath) throws IOException {
        File file = new File(filePath);

        try {
            String fileString = FileUtils.readFileToString(file, "UTF-8");

            List<String> splitElementString = Arrays.asList(fileString.split("◥█̆̈◤࿉∥"));

            return splitElementString.stream().map(textData -> {
                String[] elementInfo = textData.split("☝");

                return new Book(elementInfo[0], elementInfo[1],
                        Integer.parseInt(elementInfo[2]),
                        Integer.parseInt(elementInfo[3]),
                        elementInfo[4], elementInfo[5]);
            }).toList();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage() + " make sure that the file exists.");
            return new ArrayList<>();
        }
    }

    public static List<Magazine> loadMagazineFromDisk(String filePath) throws IOException {
        File file = new File(filePath);

        try {
            String fileString = FileUtils.readFileToString(file, "UTF-8");

            List<String> splitElementString = Arrays.asList(fileString.split("◥█̆̈◤࿉∥"));

            return splitElementString.stream().map(textData -> {
                String[] elementInfo = textData.split("☝");

                return new Magazine(elementInfo[0], elementInfo[1],
                        Integer.parseInt(elementInfo[2]),
                        Integer.parseInt(elementInfo[3]),
                        elementInfo[4]);
            }).toList();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage() + " make sure that the file exists.");
            return new ArrayList<>();
        }
    }

}
